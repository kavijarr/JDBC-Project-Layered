package Controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.Orderdto;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dao.custom.OrderDao;
import dao.custom.impl.OrderDaoimpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {
    public AnchorPane pane;
    public ComboBox customerBox;
    public ComboBox itemBox;
    public ComboBox cmbID;
    public ComboBox cmbItemCode;
    public JFXTextField txtName;
    public JFXTextField txtDesc;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXTreeTableView<OrderTm> tblOrders;
    public TreeTableColumn colCode;
    public TreeTableColumn colDesc;
    public TreeTableColumn colQty;
    public TreeTableColumn colAmount;
    public TreeTableColumn colOption;
    public Label lblTotal;
    public Label lblOrderId;
    private List<Orderdto> dtoList;

    public void BackButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.show();
            stage.setTitle("Dashboard");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CustomerDto> customers = new ArrayList<>();
    private List<ItemDto> items = new ArrayList<>();

    private CustomerBo customerBo= BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private ObservableList<OrderTm> tmList = FXCollections.observableArrayList();
    private double total=0;
    public void initialize(){
        loadCustomerIds();
        loadItemCodes();

        cmbID.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, id) -> {
            for (CustomerDto dto:customers) {
                if(dto.getId().equals(id)){
                    txtName.setText(dto.getName());
                }
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, code) -> {
            for (ItemDto dto:items) {
                if(dto.getCode().equals(code)){
                    txtDesc.setText(dto.getDesc());
                    txtUnitPrice.setText(String.format("%.2f",dto.getUnitPrice()));
                }
            }
        });

        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        genarateID();
    }

    private void loadItemCodes() {
        try {
            items = itemBo.allItems();
            ObservableList list = FXCollections.observableArrayList();
            for (ItemDto dto: items) {
                list.add(dto.getCode());
            }
            cmbItemCode.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        try {
            customers = customerBo.allCustomers();
            ObservableList list = FXCollections.observableArrayList();
            for (CustomerDto dto: customers) {
                list.add(dto.getId());
            }
            cmbID.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        ItemDto itemDto = null;
        for (ItemDto dto : items) {
            if (cmbItemCode.getValue().equals(dto.getCode())) {
                itemDto = dto;
            }
        }
        if (Integer.parseInt(txtQty.getText()) > itemDto.getQty()) {
            new Alert(Alert.AlertType.ERROR, "Item Not In Stock").show();
        } else {
            try {
                double amount = itemBo.getItem(cmbItemCode.getValue().toString()).getUnitPrice() * Integer.parseInt(txtQty.getText());
                JFXButton btn = new JFXButton("Delete");
                OrderTm tm = new OrderTm(
                        cmbItemCode.getValue().toString(),
                        txtDesc.getText(),
                        Integer.parseInt(txtQty.getText()),
                        amount,
                        btn
                );

                btn.setOnAction(actionEvent1 -> {
                    tmList.remove(tm);
                    total -= tm.getAmount();
                    lblTotal.setText(String.format("%.2f", total));
                    tblOrders.refresh();
                });

                boolean isExist = false;
                for (OrderTm order : tmList) {
                    if (order.getCode().equals(tm.getCode())) {
                        isExist = true;
                        if ((order.getQty()+Integer.parseInt(txtQty.getText()))>itemDto.getQty()){
                            new Alert(Alert.AlertType.ERROR,"Item Not In Stock").show();
                        }else{
                            order.setQty(order.getQty() + tm.getQty());
                            order.setAmount(order.getAmount() + tm.getAmount());
                            total += tm.getAmount();
                        }
                    }
                }
                if (!isExist) {
                    tmList.add(tm);
                    total += tm.getAmount();
                }
                TreeItem<OrderTm> treeObject = new RecursiveTreeItem<OrderTm>(tmList, RecursiveTreeObject::getChildren);
                tblOrders.setRoot(treeObject);
                tblOrders.setShowRoot(false);
                lblTotal.setText(String.format("%.2f", total));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void clearFeilds() {
        txtDesc.setEditable(true);
        txtUnitPrice.clear();
        txtDesc.clear();
        txtQty.clear();
    }

    public void genarateID(){
        try {
            lblOrderId.setText(orderBo.genarateId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        List<OrderDetailsDto> list = new ArrayList<>();
        for (OrderTm tm:tmList) {
            list.add(new OrderDetailsDto(
                    lblOrderId.getText(),
                    tm.getCode(),
                    tm.getQty(),
                    tm.getAmount()/tm.getQty()
            ));
        }
        if (!tmList.isEmpty()){
            boolean isSaved = false;
            try {
                isSaved = orderBo.saveOrder(new Orderdto(
                        lblOrderId.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                        cmbID.getValue().toString(),
                        list
                ));
//                orderBo.removeFromStock(list,items);
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION,"Order Saved!").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Somthing went wrong!").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
