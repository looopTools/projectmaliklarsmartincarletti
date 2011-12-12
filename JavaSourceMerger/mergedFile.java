
---- /Users/tools/.Trash/Project_Carletti_CAOS/src/carletti/ConnectionData.java

package carletti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionData {
	public ConnectionData(){
		String reguest = "SELECT regnr, ktonr  " +
				"FROM KundeHarKonto khk, Kunde k " +
				"WHERE k.cprNr=" + "'" + "kid" + "' " +
				" AND k.cprNr=khk.cprNr";
		System.out.println(reguest);
		try {
			Connection myConnection;
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://10.37.129.3:1433/AndbyBank", "sa",
					"lnc20020"); // "jdbc:jtds:sqlserver://10.37.129.3:1433/Bank"
			Statement stmt = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(reguest);
			while (res.next()) {
				int regNr = res.getInt(1);
				long ktonr = res.getLong(2);
				System.out.println("RegNr: " + regNr  + " Konto nr: " + ktonr);
//				String navn = res.getString(2);
//				String adresse = res.getString(3);
//				String tlf = res.getString(4);
//				System.out.println("RegNr: " + regNr + " Navn: " + navn + " Adresse: " + adresse + " Tlf: " + tlf);
			}
			if (res != null)
				res.close();
			if (stmt != null)
				stmt.close();
			if (myConnection != null)
				myConnection.close();
		} catch (Exception e) {
			System.out.println("error:  " + e.getMessage());
		}
	}
}

---- /Users/tools/.Trash/Project_Carletti_CAOS/src/carletti/Template.java

//Package declearation
package carletti;


//Imports


/**
 * 
 * @group Martin R. Bundgaard, Malik L. Lund, Lars Nielsen
 * @author Lars Nielsen
 * @class
 *
 */


public class Template {

	//Fields
	
	//Constructor
	
	//Methods
}

---- /Users/tools/.Trash/samlet.java

package carletti.dao.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RecreateDatabase
{
    public static void main(String[] args) {
        try {
            //connection to MySQL
        	Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://10.37.129.3/master", "sa",
					"lnc20020"); // "jdbc:jtds:sqlserver://10.37.129.3:1433/Cars"
			
            
            Statement stmt = myConnection.createStatement();
            stmt.executeUpdate("DROP DATABASE Carletti");
            stmt.executeUpdate("CREATE DATABASE Carletti");
            
            System.out.println("Database recreated!");
        }
        catch (Exception e) {
            System.out.println("Error connecting to database:  "
                    + e.getMessage());
        }
    }
}
package carletti.dao;

import java.util.List;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * @author Malik Lund
 *
 */
public interface Dao {

    public List<SubProduct> getSubProducts(State state);
    public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state);
    public void storeTreatment(Treatment t);
    public void removeTreatment(Treatment t);
    public void storeProduct(Product p);
    public void removeProduct(Product p);
    public void storeSubProduct(SubProduct sp);
    public void removeSubProduct(SubProduct sp);
    public void storePosition(Position p);
    public void removePosition(Position p);
    public List<Treatment> getTreatments();
    public List<Product> getProducts();
    public List<SubProduct> getSubProducts();
    public List<Position> getPositions();
}
/**
 * @author Lars Nielse, Malik L. Lund, Matrin R. Bungaard
 * @class JpaDao
 * @programmer Lars Nielsen
 */
package carletti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

public class JpaDao implements Dao{

    private static JpaDao uniqueInstance;
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    private JpaDao(){
        emf = Persistence.createEntityManagerFactory("Carletti");
        em = emf.createEntityManager();
    }
    
    /**
     * insures that we are only able to create on JpaDao instance
     * and it is done by implementing the Singelton design pattern
     * @return
     */
    public static JpaDao getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new JpaDao();
        }
        
        return uniqueInstance;
    }
    
    @Override
    /**
     * @author Malik Lund
     */
    public void storeSubProduct(SubProduct sp){
        em.getTransaction().begin();
        em.persist(sp);
        em.getTransaction().commit();
    }
    
    public void close(){
        em.close();
        emf.close();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<SubProduct> getSubProducts(State state) {
        Query query = em.createQuery("SELECT sp FROM SubProduct sp WHERE sp.state = :state");
        query.setParameter("state", state);
        return query.getResultList();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state) {
        em.getTransaction().begin();
        subProduct.setState(state);
        em.getTransaction().commit();
        return subProduct;
    }
    @Override
    /**
     * @author Malik Lund
     */
    public void storeProduct(Product p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void removeProduct(Product p) {
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void removeSubProduct(SubProduct sp) {
        em.getTransaction().begin();
        em.remove(sp);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<Product> getProducts() {
        Query query = em.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<SubProduct> getSubProducts() {
        Query query = em.createQuery("SELECT sp FROM SubProduct sp", SubProduct.class);
        return query.getResultList();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void storeTreatment(Treatment t) {
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void removeTreatment(Treatment t) {
        em.getTransaction().begin();
        em.remove(t);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<Treatment> getTreatments() {
        Query query = em.createQuery("SELECT t FROM Treatment t", Treatment.class);
        return query.getResultList();
    }
    
    //-----Lars
    @Override
    public List<Position> getPositions(){
        Query query = em.createQuery("SELECT p FROM Position p", Position.class);
        return query.getResultList();
    }

    @Override
    public void storePosition(Position p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        
        
    }

    @Override
    public void removePosition(Position p) {
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        
    }
    //-----
}
package carletti.dao;

import java.util.ArrayList;
import java.util.List;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * This class maintains connection, and any operations, with the database.
 * 
 * It is implemented as a Singleton-object to ensure there is only one
 * object interacting with the database.
 * To get an instance call the static method Dao.getInstance().
 * 
 * TODO Change implementation to use a database rather than lists.
 * 
 * @author Malik Lund
 *
 */
public class LocalDao implements Dao {
    private static LocalDao dao = null;
    private ArrayList<Treatment> treatments;
    private ArrayList<Product> products;
    private ArrayList<SubProduct> subproducts;
    private ArrayList<Position> positions;
    private int subProductnextID = 1;
    private int productNextID = 1;
    /**
     * Simply creates a Dao-object and initializes all
     * lists.
     */
    private LocalDao(){
        treatments = new ArrayList<Treatment>();
        products = new ArrayList<Product>();
        subproducts = new ArrayList<SubProduct>();      
        positions = new ArrayList<Position>();
    }
    
    /**
     * Creates a new instance of the Dao class on the first call
     * and merely returns this for any subsequent calls. 
     * @return An instance of Dao.
     */
    public static Dao getInstance(){
        if (dao == null){
            dao = new LocalDao();
        }
        return dao;
    }
    
    /**
     * Returns a list of SubProducts in the given state.
     * @param state The state of the returned SubProducts. 
     * @return A list of SubProducts in a given state. If state is null
     *         all SubProduct objects are returned.
     */
    public List<SubProduct> getSubProducts(State state){
        ArrayList<SubProduct> selectedSubproducts = new ArrayList<SubProduct>();
        for (SubProduct sp: subproducts){
            if (sp.getState().equals(state) || state == null){
                selectedSubproducts.add(sp);
            }
        }
        return selectedSubproducts;
    }
    
    /**
     * Changes the state of a SubProduct-object and saves it in the
     * database.
     * @param subProduct The object to change the state of.
     * @param state The state to change to.
     * @return The object with its new state.
     */
    public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state){
        subProduct.setState(state);
        return subProduct;
    }
    
    /**
     * Store a Product object in the database.
     * @param p The Product object to store.
     */
    public void storeProduct(Product p){
        Treatment t = p.getTreatment();
        if (!treatments.contains(t)){
            storeTreatment(t);
        }
        products.add(p);
    }
    
    /**
     * Delete a product type from the system.
     * @param p The Product object to delete.
     */
    public void removeProduct(Product p){
        products.remove(p);
    }

    /**
     * Store a SubProduct object in the database.
     * @param sp The SubProduct object to store.
     */
    public void storeSubProduct(SubProduct sp){
        subproducts.add(sp);
        
    }
    
    /**
     * Delete a sub-product from the system.
     * @param sp The SubProduct object to delete.
     */
    public void removeSubProduct(SubProduct sp){
        subproducts.remove(sp);
    }
    
    /**
     * Get a list of all product types.
     * @return A list of Product objects.
     */
    public List<Product> getProducts(){
        return new ArrayList<Product>(products);
    }
    
    /**
     * Get a list of all sub-product types.
     * @return A list of SubProduct objects.
     */
    public List<SubProduct> getSubProducts(){
        return new ArrayList<SubProduct>(subproducts);
    }
    
    public int getSubProducNextID(){
        return subProductnextID;
    }
    
    public void countSubProducID(){
        subProductnextID++;
    }
    
    public int getProducNextID(){
        return productNextID;
    }
    
    public void countProducID(){
        productNextID++;
    }

    @Override
    public void storeTreatment(Treatment t) {
        treatments.add(t);
    }

    @Override
    public void removeTreatment(Treatment t) {
        treatments.remove(t);
    }

    @Override
    public List<Treatment> getTreatments() {
        return new ArrayList<Treatment>(treatments);
    }
    
    //-----Lars
    @Override
    public List<Position> getPositions() {
        return new ArrayList<Position>(positions);
    }

    
    @Override
    public void storePosition(Position p) {
        positions.add(p);
        
    }

    @Override
    public void removePosition(Position p) {
        positions.remove(p);
        
    }
    //-----
    
    
}
package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;

/**
 * The CreateNewProductDialog class handles the creation
 * of a new product and it's associated treatment plan.
 * @author Malik Lund
 *
 */
public class CreateNewProductDialog extends JDialog {
    private Service service;
    private Controller controller;
    
    private boolean newTreatment;
    
    private JPanel mainPanel, productAndSubproductPanel, productInfoPanel,
                   productButtonsPanel, treatmentNamePanel, subTreatmentPanel, subTreatmentButtonsPanel;
    private JLabel lblNewProduct, lblName, lblDescription, lblTreatmentName, lblSubTreatment;
    private JTextField txfName, txfTreatmentName;
    private JTextArea txtAreaDescription;
    private JButton btnCreate, btnCancel, btnAddSubTreatment, btnSelectTreatment;
    private JTable subTreatmentsTable;
    private NewProductSubTreatmentsTableModel subTreatmentsTableModel;
    private JScrollPane productDescriptionScrollPane, subTreatmentsScrollPane;

    /**
     * 
     */
    public CreateNewProductDialog(){
        service = service.getInstance();
        this.setTitle("Create new product");
        this.setModal(true);

        controller = new Controller();
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);
        
        productAndSubproductPanel = new JPanel();
        GroupLayout outerGroupLayout = new GroupLayout(productAndSubproductPanel);
        productAndSubproductPanel.setLayout(outerGroupLayout);
        
        outerGroupLayout.setAutoCreateGaps(true);
        outerGroupLayout.setAutoCreateContainerGaps(true);
        
        productInfoPanel = new JPanel();
        productButtonsPanel = new JPanel();
        subTreatmentPanel = new JPanel();
        subTreatmentButtonsPanel = new JPanel();
        lblNewProduct = new JLabel("New Product");
        
        mainPanel.add(lblNewProduct, BorderLayout.NORTH);
        
        //------------
        // General grouping of product information and subtreatment information
        
        outerGroupLayout.setHorizontalGroup(
            outerGroupLayout.createSequentialGroup()
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(productInfoPanel)
                    .addComponent(productButtonsPanel))
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(subTreatmentPanel)
                    .addComponent(subTreatmentButtonsPanel))
        );
        
        outerGroupLayout.setVerticalGroup(
            outerGroupLayout.createSequentialGroup()
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(productInfoPanel)
                    .addComponent(subTreatmentPanel))
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(productButtonsPanel)
                    .addComponent(subTreatmentButtonsPanel))
        );
        
        mainPanel.add(productAndSubproductPanel);
        
        //------------
        // Product name and description
        
        GroupLayout productGroupLayout = new GroupLayout(productInfoPanel);
        productInfoPanel.setLayout(productGroupLayout);
        
        productGroupLayout.setAutoCreateGaps(true);
        productGroupLayout.setAutoCreateContainerGaps(true);
        
        lblName = new JLabel("Name:");
        lblDescription = new JLabel("Description:");
        txfName = new JTextField();
        txtAreaDescription = new JTextArea();
        txtAreaDescription.setLineWrap(true);
        productDescriptionScrollPane = new JScrollPane(txtAreaDescription);
        
        productGroupLayout.setHorizontalGroup(
            productGroupLayout.createSequentialGroup()
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(lblDescription))
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txfName, 150, 175, 200)
                    .addComponent(productDescriptionScrollPane, 250, 250, Short.MAX_VALUE))
        );
        
        productGroupLayout.setVerticalGroup(
            productGroupLayout.createSequentialGroup()
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txfName))
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescription)
                    .addComponent(productDescriptionScrollPane, 100, 100, Short.MAX_VALUE))
        );
        
        //--------------
        // Product create and cancel buttons
        
        GroupLayout productButtonsGroupLayout = new GroupLayout(productButtonsPanel);
        productButtonsPanel.setLayout(productButtonsGroupLayout);
        
        productButtonsGroupLayout.setAutoCreateGaps(true);
        productButtonsGroupLayout.setAutoCreateContainerGaps(true);
        
        btnCreate = new JButton("Create");
        btnCancel = new JButton("Cancel");
        
        btnCreate.addActionListener(controller);
        btnCancel.addActionListener(controller);
        
        productButtonsGroupLayout.setHorizontalGroup(
            productButtonsGroupLayout.createSequentialGroup()
                .addComponent(btnCreate)
                .addComponent(btnCancel)
        );
        
        productButtonsGroupLayout.setVerticalGroup(
            productButtonsGroupLayout.createSequentialGroup()
                .addGroup(productButtonsGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnCancel))
        );
        
        //-------------
        // Subtreatments table
        
        GroupLayout subTreatmentsGroupLayout = new GroupLayout(subTreatmentPanel);
        subTreatmentPanel.setLayout(subTreatmentsGroupLayout);
        
        subTreatmentsGroupLayout.setAutoCreateGaps(true);
        subTreatmentsGroupLayout.setAutoCreateContainerGaps(true);
        
        lblSubTreatment = new JLabel("Treatments");
        treatmentNamePanel = new JPanel();
        lblTreatmentName = new JLabel("Name:    ");
        txfTreatmentName = new JTextField();
        subTreatmentsTableModel = new NewProductSubTreatmentsTableModel();
        subTreatmentsTable = new JTable(subTreatmentsTableModel);
        subTreatmentsScrollPane = new JScrollPane(subTreatmentsTable);
        
        treatmentNamePanel.setLayout(new BoxLayout(treatmentNamePanel, BoxLayout.X_AXIS));
        treatmentNamePanel.add(lblTreatmentName);
        treatmentNamePanel.add(txfTreatmentName);
        txfTreatmentName.setEnabled(false);
        subTreatmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        subTreatmentsGroupLayout.setHorizontalGroup(
            subTreatmentsGroupLayout.createSequentialGroup()
                .addGroup(subTreatmentsGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblSubTreatment)
                    .addComponent(treatmentNamePanel)
                    .addComponent(subTreatmentsScrollPane, 400, 400, 400))
        );
        
        subTreatmentsGroupLayout.setVerticalGroup(
            subTreatmentsGroupLayout.createSequentialGroup()
                .addComponent(lblSubTreatment)
                .addComponent(treatmentNamePanel)
                .addComponent(subTreatmentsScrollPane, 100, 100, Short.MAX_VALUE)
        );
        
        //-----------
        // Treatment buttons.
        
        GroupLayout subTreatmentButtonsGroupLayout = new GroupLayout(subTreatmentButtonsPanel);
        subTreatmentButtonsPanel.setLayout(subTreatmentButtonsGroupLayout);
        
        subTreatmentButtonsGroupLayout.setAutoCreateGaps(true);
        subTreatmentButtonsGroupLayout.setAutoCreateContainerGaps(true);
        
        btnAddSubTreatment = new JButton("New Treatment");
        btnSelectTreatment = new JButton("Select existing treatment");
        
        btnAddSubTreatment.addActionListener(controller);
        btnSelectTreatment.addActionListener(controller);
        
        subTreatmentButtonsGroupLayout.setHorizontalGroup(
            subTreatmentButtonsGroupLayout
                .createSequentialGroup()
                    .addComponent(btnAddSubTreatment)
                    .addComponent(btnSelectTreatment)
        );
        
        subTreatmentButtonsGroupLayout.setVerticalGroup(
            subTreatmentButtonsGroupLayout
                .createParallelGroup()
                    .addComponent(btnAddSubTreatment)
                    .addComponent(btnSelectTreatment)
        );
        
        this.pack();
    }
    
    /**
     * Handles all input for this class.
     * @author Malik Lund
     *
     */
    private class Controller implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            /**
             * Create product button.
             */
            if (ae.getSource() == btnCreate){
                boolean success = true;
                
                String subProductName = txfName.getText();
                if (subProductName.length() <= 0){
                    JOptionPane.showMessageDialog(null, "Subproduct name is empty!");
                    success = false;
                }
                
                String treatmentName = txfTreatmentName.getText();
                if (success && treatmentName.length() <= 0){
                    JOptionPane.showMessageDialog(null, "Treatment name is empty!");
                    success = false;
                }
                
                List<SubTreatment> data = subTreatmentsTableModel.getData().getSubTreatments();
                if (success && data.size() <= 0){
                    JOptionPane.showMessageDialog(null, "Treatment has no subtreatments!");
                    success = false;
                }
                
                if (success){
                    Treatment treatment = service.createTreatment(txfTreatmentName.getText());
                    for (int i = 0; i < data.size(); i++){
                        String name = data.get(i).getName();
                        long min = data.get(i).getDryMin();
                        long optimal = data.get(i).getDryPrime();
                        long max = data.get(i).getDryMax();
                        treatment.createSubTreatment(name, min, optimal, max);
                    }
                    service.createProduct(txfName.getText(), txtAreaDescription.getText(), treatment);
                    CreateNewProductDialog.this.setVisible(false);
                }
            }
            /**
             * Cancel creation of product button.
             */
            else if (ae.getSource() == btnCancel){
                CreateNewProductDialog.this.setVisible(false);
            }
            
            /**
             * New/Add subtreatment button.
             */
            else if (ae.getSource() == btnAddSubTreatment){
                if (newTreatment){
                    CreateNewSubTreatmentDialog createSubTreatmentDialog = new CreateNewSubTreatmentDialog(subTreatmentsTableModel);
                    createSubTreatmentDialog.setLocationRelativeTo(CreateNewProductDialog.this);
                    createSubTreatmentDialog.setVisible(true);
                } else {
                    newTreatment = true;
                    txfTreatmentName.setEnabled(true);
                    txfTreatmentName.setText("");
                    btnAddSubTreatment.setText("Add subtreatment");
                    subTreatmentsTableModel.setData(new Treatment("Temp"));
                }
            }
            /**
             * Select treatment button. 
             */
            else if (ae.getSource() == btnSelectTreatment){
                // save new Treatment in case you regret 
                // selecting an existing one
                Treatment temporaryTreatment = null;
                if (newTreatment){
                    temporaryTreatment = subTreatmentsTableModel.getData();
                    temporaryTreatment.setName(txfTreatmentName.getText());
                }
                
                // prepare table and tablemodel
                subTreatmentsTableModel.setData(new Treatment("Temp"));
                subTreatmentsTableModel.removeTableModelListener(subTreatmentsTable); // "disconnect" table 
                txfTreatmentName.setText("");
                
                // start selection
                SelectTreatmentDialog selectTreatmentDialog = new SelectTreatmentDialog(subTreatmentsTableModel);
                selectTreatmentDialog.setLocationRelativeTo(CreateNewProductDialog.this);
                selectTreatmentDialog.setVisible(true);
                
                // handle selection
                Treatment treatment = subTreatmentsTableModel.getData();
                if (!treatment.getName().equals("Temp")){
                    // selected an existing treatment
                    txfTreatmentName.setText(treatment.getName());
                    txfTreatmentName.setEnabled(false);
                    btnAddSubTreatment.setText("New subtreatment");
                    newTreatment = false;
                } else {
                    // didn't select an existing treatment
                    if (newTreatment){
                        // revert to previous data.
                        subTreatmentsTableModel.setData(temporaryTreatment);
                        txfTreatmentName.setText(temporaryTreatment.getName());
                    } else {
                        // no previsous data.
                        subTreatmentsTableModel.setData(new Treatment("Temp"));
                    }
                }
                
                subTreatmentsTableModel.addTableModelListener(subTreatmentsTable); // "reconnect" table
                subTreatmentsTableModel.fireTableDataChanged();

            }
        }
    }
}
package carletti.gui.dialogs;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Handles the creation of a new SubTreatment and
 * associates it with creation of a new Treatment.
 * @author Malik Lund
 *
 */
public class CreateNewSubTreatmentDialog extends JDialog {

    private Controller controller;
    private JPanel mainPanel, infoPanel;
    private TimeSelectorPanel minPanel, optimalPanel, maxPanel;
    private JLabel lblNew, lblName, lblMin, lblOptimal, lblMax;
    private JTextField txfName;
    private JButton btnCreate, btnCancel;

    private NewProductSubTreatmentsTableModel subTreatmentsTableModel;
    
    /**
     * 
     * @param subTreatmentsTableModel
     */
    public CreateNewSubTreatmentDialog(NewProductSubTreatmentsTableModel subTreatmentsTableModel){
        this.getContentPane().setLayout(new FlowLayout());
        this.setModal(true);
        this.setResizable(false);

        
        this.subTreatmentsTableModel = subTreatmentsTableModel;
        controller = new Controller();
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);
        
        infoPanel = new JPanel();
        GroupLayout layout = new GroupLayout(infoPanel);
        infoPanel.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        minPanel = new TimeSelectorPanel();
        optimalPanel = new TimeSelectorPanel();
        maxPanel = new TimeSelectorPanel();
        lblNew = new JLabel("New subtreatment");
        lblName = new JLabel("Name:");
        lblMin = new JLabel("Minimum:");
        lblOptimal = new JLabel("Optimal:");
        lblMax = new JLabel("Maximum:");
        txfName = new JTextField();
        btnCreate = new JButton("Create");
        btnCancel = new JButton("Cancel");
        
        btnCreate.addActionListener(controller);
        btnCancel.addActionListener(controller);
        
        mainPanel.add(lblNew, BorderLayout.NORTH);
        
        /**
         * Define main grouping horizontally.
         */
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(lblMin)
                    .addComponent(lblOptimal)
                    .addComponent(lblMax)
                    .addComponent(btnCreate))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txfName)
                    .addComponent(minPanel)
                    .addComponent(optimalPanel)
                    .addComponent(maxPanel)
                    .addComponent(btnCancel))
        );
        
        /**
         * Define main grouping vertically.
         */
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txfName))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMin)
                    .addComponent(minPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOptimal)
                    .addComponent(optimalPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMax)
                    .addComponent(maxPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnCancel))
        );

        mainPanel.add(infoPanel);
        
        pack();
    }
    
    /**
     * This private class handles all input events.
     * @author Malik
     *
     */
    private class Controller implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent ae) {
            /**
             * Create button.
             */
            if (ae.getSource() == btnCreate){
                // --- input verification start ---
                boolean error = false;
                String name = txfName.getText();
                if (name.length() <= 0){
                    JOptionPane.showMessageDialog(null, "Name is empty!");
                    error = true;
                }
                
                long min = minPanel.getTime();
                if (!error && min < 0){
                    JOptionPane.showMessageDialog(null, "Minimum must be larger than 0!");
                    error = true;
                }
                
                long optimal = optimalPanel.getTime();
                if (!error && optimal < min){
                    JOptionPane.showMessageDialog(null, "Optimal must be larger than minimum!");
                    error = true;
                }
                
                long max = maxPanel.getTime();
                if (!error && max < optimal){
                    JOptionPane.showMessageDialog(null, "Maximum must be larger than optimal!");
                    error = true;
                }
                // --- input verification end ---
                
                if (!error){
                    subTreatmentsTableModel.newSubTreatment(name, min, optimal, max);
                    CreateNewSubTreatmentDialog.this.setVisible(false);
                }
            }
            /**
             * Cancel button.
             */
            else if (ae.getSource() == btnCancel){
                CreateNewSubTreatmentDialog.this.setVisible(false);
            }
        }
    }
    
    /**
     * Creates a panel containing four SelectorPanel objects (see below). One for
     * selecting number of days, one for number of hours, one for minutes
     * and one for seconds (it is assumed that it is unnecessary to input
     * milliseconds as values).
     * @author Malik Lund
     *
     */
    class TimeSelectorPanel extends JPanel{
        
        private JLabel lblDays, lblHours, lblMinutes, lblSeconds;
        private SelectorPanel daySelector, hourSelector, minuteSelector, secondSelector;
        
        /**
         * 
         */
        public TimeSelectorPanel(){
            super();
            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            lblDays = new JLabel("Days");
            lblHours = new JLabel("Hours");
            lblMinutes = new JLabel("Minutes");
            lblSeconds = new JLabel("Seconds");
            daySelector = new SelectorPanel(100);
            hourSelector = new SelectorPanel(24);
            minuteSelector = new SelectorPanel(60);
            secondSelector = new SelectorPanel(60);
            
            layout.setHorizontalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblDays)
                        .addComponent(daySelector))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblHours)
                        .addComponent(hourSelector))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblMinutes)
                        .addComponent(minuteSelector))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblSeconds)
                        .addComponent(secondSelector))
            );
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDays)
                        .addComponent(lblHours)
                        .addComponent(lblMinutes)
                        .addComponent(lblSeconds))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(daySelector)
                        .addComponent(hourSelector)
                        .addComponent(minuteSelector)
                        .addComponent(secondSelector))
            );
        }
        
        /**
         * Returns the time represented by all four textfields
         * represented in milliseconds.
         * @return
         */
        public long getTime(){
            long result = daySelector.getTime() * 1000 * 60 * 60 * 24;
            result += hourSelector.getTime() * 1000 * 60 * 60;
            result += minuteSelector.getTime() * 1000 * 60;
            result += secondSelector.getTime() * 1000;
            return result;
        }
    }
    
    /**
     * A panel containing a plus- and minus-button and a textfield with
     * a number. Pressing the plus button increments the value in the 
     * textfield by one, while pressing the minus button decrements the
     * value by one.
     * If the value gets larger than the given crossover point the value
     * wraps around to zero. If a value gets smaller than 0 it wraps around
     * to the larges value below the crossover point.
     * @author Malik Lund
     *
     */
    private class SelectorPanel extends JPanel implements ActionListener {
        private int crossover;
        private JTextField txfNumber;
        private JButton btnPlus, btnMinus;
        
        /**
         * 
         * @param crossover Value where the number crosses over
         */
        public SelectorPanel(int crossover){
            super();
            this.crossover = crossover;
            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            txfNumber = new JTextField("0");
            btnPlus = new JButton("+");
            btnMinus = new JButton("-");
            
            txfNumber.setHorizontalAlignment(JTextField.RIGHT);
            
            btnPlus.addActionListener(this);
            btnMinus.addActionListener(this);
            
            layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(btnPlus)
                    .addComponent(txfNumber)
                    .addComponent(btnMinus)
            );
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(btnPlus, 20, 20, 20)
                    .addComponent(txfNumber)
                    .addComponent(btnMinus, 20, 20, 20)
            );
        }
        
        /**
         * 
         * @return The the number in the text field as a long.
         * @throws NumberFormatException
         */
        public long getTime() throws NumberFormatException{
            return Long.parseLong(txfNumber.getText());
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            /**
             * Increment button.
             */
            if (ae.getSource() == btnPlus){
                int number = getNumber();
                number = (number + 1) % crossover;
                txfNumber.setText(number + "");
            }
            
            /**
             * Decrement button.
             */
            else if (ae.getSource() == btnMinus){
                int number = getNumber();
                number = (number - 1);
                if (number < 0){
                    number = crossover - 1;
                }
                txfNumber.setText(number + "");
            }
        }
        
        /**
         * Performs various safety checks on the value in the
         * text field.
         * @return
         * @throws NumberFormatException
         */
        private int getNumber() throws NumberFormatException{
            int number = 0;
            if (txfNumber.getText().length() > 0){
                number = Integer.parseInt(txfNumber.getText());
            } else {
                number = 0;
            }
            
            if (number >= crossover){
                number = crossover - 1;
            }
            if (number < 0){
                number = 0;
            }
            return number;
        }
    }
}
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.LongToStringParser;
import carletti.model.SubTreatment;
import carletti.model.Treatment;

/**
 * This class is used by the JTable in CreateNewSubTreatmentDialog.
 * It contains the current list of SubTreatment-objects to be added to a
 * new Treatment-object in a new Product-object.
 * @author Malik Lund
 *
 */
public class NewProductSubTreatmentsTableModel extends AbstractTableModel {
    // Column headers.
    private String[] coloumnNames = {"Name", "Minimum", "Optimal", "Maximum"};
    // The actual data.
    private Treatment treatment = new Treatment("temp");

    @Override
    public int getColumnCount() {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount() {
        return treatment.getSubTreatments().size();
    }
    
    @Override
    public String getColumnName(int col){
        return coloumnNames[col];
    }

    @Override
    /**
     * @return col 1: Name, col 2: Min, col 3: optimal, col 4: max
     */
    public Object getValueAt(int row, int col) {
        List<SubTreatment> subTreatments = treatment.getSubTreatments();
        if (col < 1){
            return subTreatments.get(row).getName();
        } else {
            long time;
            if (col == 1){
                time = subTreatments.get(row).getDryMin();
            }
            else if (col == 2){
                time = subTreatments.get(row).getDryPrime();
            } else {
                time = subTreatments.get(row).getDryMax();
            }
            return LongToStringParser.parseLongToString(time);
        }
    }
    
    @Override
    /**
     * Simply returns the class-type of each column.
     */
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }

    /**
     * Add a new SubTreatment to the list of subTreatments.
     * @param name Name of the subtreatment.
     * @param min Minimum drying time.
     * @param optimal Optimal or prime drying time.
     * @param max Maximum drying time.
     */
    public void newSubTreatment(String name, long min, long optimal, long max) {
        treatment.createSubTreatment(name, min, optimal, max);
        fireTableDataChanged();
    }
    
    /**
     * Returns a temporary treatment containing the subtreatments
     * @return A Treatment object containing the SubTreatments. OBS! Recreate using
     *         the Service class!! This is only temporary and it will not be saved
     *         in the database!
     */
    public Treatment getData(){
        return treatment;
    }
    
    /**
     * Replace the data in the table with the
     * SubTreatments in the given Treatment.
     * @param subTreatments
     */
    public void setData(Treatment t){
        treatment = t;
        fireTableDataChanged();
    }

}
package carletti.gui.dialogs;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import carletti.model.Position;
import carletti.model.Product;
import carletti.service.Service;

/**
 *@group Lars Nielsen, Malik Lasse Lund, Martin RÃ¸nn Bundgaard
 *@class NewSubProductDialog
 *@programmer Lars Nielsen
 */
/*
 * This class is harcoded due to certain requriments, which couldn't 
 * be achive with JWindowsBuild
 */
public class NewSubProductDialog extends JDialog {
    
    private Service service; // Holds an singelton instance of the Service
    
    //GUI componenst
    private JTextField txfName;
    private JComboBox comboPos;
    private JLabel lblName, lblProd;
    private JScrollPane jspProducts;
    private JList prodList;
    private JButton btnSub, btnCan;
    
    //Start coordinates for the different GUI objects
    private int x = 20, y = 20;
    
    //Dimensions for the GUI components
    private Dimension defaultSize = new Dimension(360, 360);
    private Dimension btnSize = new Dimension(140, 20);
    private Dimension lblSize = new Dimension(140, 20);
    private Dimension txfSize = new Dimension(160, 20);
    private Dimension jspSize = new Dimension(320, 160);

    private Controller btnCtrl; //Holds an instance of the Controller 

    public NewSubProductDialog() {
        
        service = Service.getInstance(); //Service is instantiated 
        
        btnCtrl = new Controller(); //Controller is instantiated 

        //Generel preference  for the JDialog
        this.setSize(defaultSize);
        this.setResizable(false);
        this.setTitle("New Subproduct");
        this.setLayout(null);
        this.setModal(true);

        /*
         * Instansation of all GUI componetens and the preferences 
         */
        lblName = new JLabel();
        lblName.setText("Name");
        lblName.setSize(lblSize);
        lblName.setLocation(x, y);
        this.add(lblName);

        x += 160;

        txfName = new JTextField();
        txfName.setSize(txfSize);
        txfName.setLocation(x, y);
        this.add(txfName);
        
        y += 40;
        
        comboPos = new JComboBox(getPositionsAvailable().toArray());
        comboPos.setSize(txfSize);
        comboPos.setLocation(x, y);
        this.add(comboPos);

        x = 20;
        

        lblProd = new JLabel();
        lblProd.setText("Products");
        lblProd.setSize(lblSize);
        lblProd.setLocation(x, y);
        this.add(lblProd);

        y += 40;

        prodList = new JList();
        prodList.setListData(service.getProducts().toArray());
        prodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jspProducts = new JScrollPane(prodList);
        jspProducts.setSize(jspSize);
        jspProducts.setLocation(x, y);
        this.add(jspProducts);

        y += 180;

        btnSub = new JButton("Submit");
        btnSub.addActionListener(btnCtrl);
        btnSub.setSize(btnSize);
        btnSub.setLocation(x, y);
        this.add(btnSub);

        x += 180;

        btnCan = new JButton("Cancel");
        btnCan.addActionListener(btnCtrl);
        btnCan.setSize(btnSize);
        btnCan.setLocation(x, y);
        this.add(btnCan);

    }

    /**
     * Allows to set the visibility in the Controller class
     * @param visibility
     */
    private void setDialogVisibility(boolean visibility) {
        this.setVisible(visibility);
    }
    
    /**
     * Used to get all positions which is not occurpied by a SubProudct
     * @return
     */
    private List<Position> getPositionsAvailable(){
        
        ArrayList<Position> avaPos = new ArrayList<Position>();
        List<Position> allPos = service.getPositions();
        
        for(int i = 0; i < allPos.size(); i++){
            if(allPos.get(i).getSubProduct() == null){
                avaPos.add(allPos.get(i));
            }
        }
        return avaPos;
    }

    /**
     * The inner class Controller, is orgnaised so we can controll which button
     * events is actived
     */
    private class Controller implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource().equals(btnSub)) {
                String name = txfName.getText();
                Product p = (Product) prodList.getSelectedValue();
                Position pos = (Position) comboPos.getSelectedItem();
                
                service.createSubProduct(name, p, pos);
                NewSubProductDialog.this.setVisible(false);

            } else if (ae.getSource().equals(btnCan)) {
                setDialogVisibility(false);
            }
        }
    }   
}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import carletti.gui.*;
import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import carletti.gui.UpdaterThread;
import carletti.model.Position;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;
import carletti.dao.JpaDao;

public class NextSubTreatmentDialog extends JFrame
{
    private Service service;
    
    private Dimension minsize = new Dimension(600, 400);
    private JTable nextSubProcuctTable;
    private JButton btnNextSubTreatment;
    private JButton btnAnnuller;
    private NextSubTreatmentDialogTableModel tableModel;
    private JScrollPane nextSubProcuctScrollPane;
    private Controller btnCtrl;
    private NextSubTreatmentDialogTableModel nextSubTreatmentDialogTableModel;

    public NextSubTreatmentDialog()
    {
        service = Service.getInstance();
        btnCtrl = new Controller();
        this.setMinimumSize(minsize);

        tableModel = new NextSubTreatmentDialogTableModel();
        nextSubProcuctTable = new JTable(tableModel);

        nextSubProcuctScrollPane = new JScrollPane(nextSubProcuctTable);
        nextSubProcuctScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(nextSubProcuctScrollPane, BorderLayout.CENTER);

        btnNextSubTreatment = new JButton("Next Subtreatment");
        btnNextSubTreatment.addActionListener(btnCtrl);

        btnAnnuller = new JButton("Cancel");
        btnAnnuller.addActionListener(btnCtrl);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
        .createParallelGroup(Alignment.TRAILING).addComponent(nextSubProcuctScrollPane,
        GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE).addGroup(groupLayout
        .createSequentialGroup()
        .addComponent(btnNextSubTreatment)
        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(btnAnnuller)))
        .addGap(0)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
        .addComponent(nextSubProcuctScrollPane, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        .addPreferredGap(ComponentPlacement.RELATED)
        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        .addComponent(btnNextSubTreatment)
        .addComponent(btnAnnuller))
        .addGap(3)));
        
        getContentPane().setLayout(groupLayout);

        this.setVisible(true);
    }

    public void update()
    {

    }

    private class Controller implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {

            if (ae.getSource().equals(btnAnnuller))
            {
                NextSubTreatmentDialog.this.setVisible(false);
            }

            else if (ae.getSource().equals(btnNextSubTreatment))
            {
                int i = nextSubProcuctTable.getSelectedRow();
                if (i < 0)
                {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                } 
                else {
                    // --- Malik Lund start---
                    SubProduct sp = service.getSubProducts(State.TREATMENT).get(i);
                    
                    if (sp.getCurrentSubTreatmentIndex() < sp.getSubtreatments().size() - 1){
                        Position p = new Position("null");
                        PositionSelectionDialog selectionDialog = new PositionSelectionDialog(p);
                        selectionDialog.setVisible(true);
                        
                        if (!p.getPosID().equals("null")){
                            List<Position> positions = service.getPositions();
                            boolean found = false;
                            int index = 0;
                            Position wantedPosition = null;
                            while (!found && index < positions.size()){
                                wantedPosition = positions.get(i);
                                if (wantedPosition.getPosID().equals(p.getPosID())){
                                    found = true;
                                } else {
                                    i++;
                                }
                            }
                            
                            if (found){
                                service.nextTreatnemt(sp);
                                sp.setPosition(wantedPosition);
                                NextSubTreatmentDialog.this.setVisible(false);
                            }
                        }
                        
                    } else {
                        service.nextTreatnemt(sp);
                        NextSubTreatmentDialog.this.setVisible(false);
                    }
                    // --- Malik Lund end ---
                }
            }
        }
    }
    
    /**
     * 
     * @author Malik Lund
     */
    private class PositionSelectionDialog extends JDialog implements ActionListener{
        private Position position;
        
        private JPanel mainPanel, buttonsPanel;
        private JComboBox comboBox;
        private JButton btnOk, btnCancel;
        
        public PositionSelectionDialog(Position position){
            this.position = position;
            this.setLocationRelativeTo(NextSubTreatmentDialog.this);
            this.setModal(true);
            
            mainPanel = new JPanel();
            
            GroupLayout layout = new GroupLayout(this.getContentPane());
            this.getContentPane().setLayout(layout);
            
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            btnOk = new JButton("Ok");
            btnCancel = new JButton("Cancel");
            comboBox = new JComboBox(getPositionsAvailable().toArray());
            
            buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
            buttonsPanel.add(btnOk);
            buttonsPanel.add(btnCancel);
            
            btnOk.addActionListener(this);
            btnCancel.addActionListener(this);
            
            layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(comboBox)
                    .addComponent(buttonsPanel)
            );
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(comboBox)
                    .addComponent(buttonsPanel)
            );
            
            this.pack();
        }
        
        private List<Position> getPositionsAvailable(){
            
            ArrayList<Position> avaPos = new ArrayList<Position>();
            List<Position> allPos = service.getPositions();
            
            for(int i = 0; i < allPos.size(); i++){
                if(allPos.get(i).getSubProduct() == null){
                    avaPos.add(allPos.get(i));
                }
            }
            return avaPos;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == btnOk){
                position.setPosID(((Position)comboBox.getSelectedItem()).getPosID());
                this.setVisible(false);
            }
            else if (ae.getSource() == btnCancel){
                this.setVisible(false);
            }
        }
    }
}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;
import carletti.dao.JpaDao;

public class NextSubTreatmentDialogTableModel extends AbstractTableModel
{
    private Service service;
    // Column headers.
    private String[] coloumnNames =
    { "ID", "Name", "Subtreatment", "Number of Subtreatments" };
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State treatment = State.TREATMENT;

    public NextSubTreatmentDialogTableModel()
    {
        service = Service.getInstance();
        newData = service.getSubProducts(treatment);
    }

    @Override
    public int getColumnCount()
    {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return newData.size();
    }

    @Override
    public String getColumnName(int col)
    {
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        SubProduct sp = newData.get(row);
        Object[] value =
        {

        new Integer(sp.getId()), sp.getName(),
                sp.getCurrentSubTreatment().getName(),
                sp.getSubtreatments().size() };
        return value[col];
    }

    @Override
    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    public SubProduct selctedSubProduct(int selectedRow)
    {
        if ( selectedRow >= 0)
        {
            return newData.get(selectedRow);
        }
        else
            return null;
    }

    public void updateData()
    {
        newData = service.getSubProducts(treatment);
    }
        
}
package carletti.gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;

/**
 * Handles the selection of an existing treatment plan.
 * This treatment plan is then associated with a
 * product in creation.
 * @author Malik Lund
 */
public class SelectTreatmentDialog extends JDialog {
    
    private Service service;
    private NewProductSubTreatmentsTableModel tableModel;
    private Controller controller;
    private List<Treatment> treatments;
    
    private JPanel buttonsPanel;
    private JLabel lblTreatments, lblSubTreatments;
    private JList treatmentsList;
    private JScrollPane treatmentsListScrollPane, subTreatmentsTableScrollPane;
    private JTable subTreatmentsTable;
    private JButton btnSelect, btnCancel;
    
    public SelectTreatmentDialog(NewProductSubTreatmentsTableModel tableModel){
        this.setModal(true);
        this.setTitle("Select treatment");
        
        service = Service.getInstance();
        this.tableModel = tableModel;
        controller = new Controller();
        
        lblTreatments = new JLabel("Treatments:");
        treatments = service.getTreatments();
        String[] treatmentsArray = new String[treatments.size()];
        for (int i = 0; i < treatmentsArray.length; i++){
            treatmentsArray[i] = treatments.get(i).getName();
        }
        treatmentsList = new JList(treatmentsArray);
        treatmentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treatmentsList.addListSelectionListener(controller);
        treatmentsListScrollPane = new JScrollPane(treatmentsList);

        lblSubTreatments = new JLabel("Subtreatments");
        subTreatmentsTable = new JTable(tableModel);
        subTreatmentsTableScrollPane = new JScrollPane(subTreatmentsTable);

        buttonsPanel = new JPanel();
        btnSelect = new JButton("Select");
        btnSelect.addActionListener(controller);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(controller);
        buttonsPanel.add(btnSelect);
        buttonsPanel.add(btnCancel);
        
        
        // ----------------
        // Main layout
        
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblTreatments)
                    .addComponent(treatmentsListScrollPane)
                    .addComponent(buttonsPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblSubTreatments)
                    .addComponent(subTreatmentsTableScrollPane))
        );
            
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTreatments)
                    .addComponent(lblSubTreatments))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(treatmentsListScrollPane)
                    .addComponent(subTreatmentsTableScrollPane))
                .addComponent(buttonsPanel)
        );
        
        // ---------------------
        // buttons
        GroupLayout buttonsLayout = new GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsLayout);
        buttonsLayout.setAutoCreateGaps(true);
        buttonsLayout.setAutoCreateContainerGaps(true);
        
        buttonsLayout.setHorizontalGroup(
            buttonsLayout.createSequentialGroup()
                .addComponent(btnSelect)
                .addComponent(btnCancel)
        );
        
        buttonsLayout.setVerticalGroup(
            buttonsLayout.createSequentialGroup()
                .addGroup(buttonsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelect)
                    .addComponent(btnCancel))
        );
        
        this.pack();

    }
    
    private class Controller implements ActionListener, ListSelectionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            /**
             * Select button
             */
            if (ae.getSource() == btnSelect){
                SelectTreatmentDialog.this.setVisible(false);
            }
            /**
             * Cancel button
             */
            else if (ae.getSource() == btnCancel){
                tableModel.setData(new Treatment("Temp")); // "reset" table
                SelectTreatmentDialog.this.setVisible(false);
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            /**
             * Update table with selected treatment from the list.
             */
            if (lse.getSource() == treatmentsList){
                int index = treatmentsList.getSelectedIndex();
                if (index >= 0){
                    Treatment t = treatments.get(index);
                    if (t.getName().equals(treatmentsList.getSelectedValue())){
                        tableModel.setData(t);
                    }
                }
            }
        }
    }
}
/**
 * @author Martin
 */

package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import carletti.service.Service;

public class ShowDoneDialog extends JFrame
{
    
    private Dimension minsize = new Dimension(600, 400);
    private Service service;
    private Controller btnCtrl;
    private JButton btnOk;
    private JScrollPane scrollPane;
    private ShowDoneDialogTableModel tableModel;
    private JTable doneTable;
    
    public ShowDoneDialog()
    {
        
        btnCtrl = new Controller();
        service = Service.getInstance();
        this.setMinimumSize(minsize);

        tableModel = new ShowDoneDialogTableModel();
        doneTable = new JTable(tableModel);

        scrollPane = new JScrollPane(doneTable);
        scrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        btnOk = new JButton("OK");
        btnOk.addActionListener(btnCtrl);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                    .addGap(0))
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnOk)
                    .addContainerGap(527, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnOk)
                    .addContainerGap())
        );
        
        getContentPane().setLayout(groupLayout);

        this.setVisible(true);
        
    }

    private class Controller implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            if (ae.getSource().equals(btnOk))
            {
                ShowDoneDialog.this.setVisible(false);
            }
        }
    }       
}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class ShowDoneDialogTableModel extends AbstractTableModel
{
    private Service service;
    // Column headers.
    private String[] coloumnNames =
    { "ID", "Name", "Subtreatment", "Number of Subtreatments" };
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State done = State.DONE;

    public ShowDoneDialogTableModel()
    {
        service = Service.getInstance();
        newData = service.getSubProducts(done);
    }

    @Override
    public int getColumnCount()
    {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return newData.size();
    }

    @Override
    public String getColumnName(int col)
    {
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        SubProduct sp = newData.get(row);
        Object[] value =
        {

        new Integer(sp.getId()), sp.getName(),
                sp.getCurrentSubTreatment().getName(),
                sp.getSubtreatments().size() };
        return value[col];
    }

    @Override
    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    public SubProduct selctedSubProduct(int selectedRow)
    {
        if ( selectedRow >= 0)
        {
            return newData.get(selectedRow);
        }
        else
            return null;
    }

    public void updateData()
    {
        newData = service.getSubProducts(done);
    }
}
/**
 * @author Martin
 */

package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import carletti.service.Service;


public class ShowWastedDialog extends JFrame
{
    
    private Dimension minsize = new Dimension(600, 400);
    private Service service;
    private Controller btnCtrl;
    private JButton btnOk;
    private JScrollPane scrollPane;
    private ShowWastedDialogTableModel tableModel;
    private JTable wastedTable;
    
    public ShowWastedDialog()
    {
    btnCtrl = new Controller();
    service = Service.getInstance();
    this.setMinimumSize(minsize);

    tableModel = new ShowWastedDialogTableModel();
    wastedTable = new JTable(tableModel);

    scrollPane = new JScrollPane(wastedTable);
    scrollPane
            .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    getContentPane().add(scrollPane, BorderLayout.CENTER);

    btnOk = new JButton("OK");
    btnOk.addActionListener(btnCtrl);

    GroupLayout groupLayout = new GroupLayout(getContentPane());
    groupLayout.setHorizontalGroup(
        groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup()
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                .addGap(0))
            .addGroup(groupLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnOk)
                .addContainerGap(527, Short.MAX_VALUE))
    );
    groupLayout.setVerticalGroup(
        groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(groupLayout.createSequentialGroup()
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(btnOk)
                .addContainerGap())
    );
    
    getContentPane().setLayout(groupLayout);

    this.setVisible(true);
    
}

private class Controller implements ActionListener
{
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource().equals(btnOk))
        {
            ShowWastedDialog.this.setVisible(false);
        }
    }
}


}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class ShowWastedDialogTableModel extends AbstractTableModel
{
    private Service service;
    // Column headers.
    private String[] coloumnNames =
    { "ID", "Name", "Subtreatment", "Number of Subtreatments" };
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State wasted = State.WASTE;

    public ShowWastedDialogTableModel()
    {
        service = Service.getInstance();
        newData = service.getSubProducts(wasted);
    }

    public int getColumnCount()
    {
        return coloumnNames.length;
    }

    public int getRowCount()
    {
        return newData.size();
    }

    public String getColumnName(int col)
    {
        return coloumnNames[col];
    }

    public Object getValueAt(int row, int col)
    {
        SubProduct sp = newData.get(row);
        Object[] value =
        {

        new Integer(sp.getId()), sp.getName(),
                sp.getCurrentSubTreatment().getName(),
                sp.getSubtreatments().size() };
        return value[col];
    }

    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    public SubProduct selctedSubProduct(int selectedRow)
    {
        if ( selectedRow >= 0)
        {
            return newData.get(selectedRow);
        }
        else
            return null;
    }

    public void updateData()
    {
        newData = service.getSubProducts(wasted);
    }
}
package carletti.gui.dialogs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carletti.model.State;
import carletti.model.SubProduct;

/**
 * @group Lars Nielsen, Malik Lasse Lund, Martin RÂ¿n Bundgaard
 * @class SubProductDialog
 * @author Lars Nielsen
 */
public class SubProductDialog extends JDialog{

    private SubProduct sub; //holds the current sub product which is wanted for inspection
    
    /*
     *GUI components 
     */
    private JPanel panel;
    private JLabel labID, labName, labState, labProduct;
    private JTextField txfID, txfName, txfState, txfProd;
    private JButton btnOK;
    private Controller btnCtrl;
    
    public SubProductDialog(SubProduct sub){
        
        this.sub = sub;
        btnCtrl = new Controller();
        
        //Preferences for the dialog 
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
        
        //GUI components and their preferences
        panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new GridLayout(6, 2, 20, 20));
        
        labID = new JLabel();
        labID.setText("ID:");
        panel.add(labID);
        
        txfID = new JTextField();
        txfID.setEditable(false); //User should not be able to change the info from heir
        panel.add(txfID);
        
        labName = new JLabel();
        labName.setText("Name:");
        panel.add(labName);
        
        txfName = new JTextField();
        txfName.setEditable(false);
        panel.add(txfName);
        
        labState = new JLabel();
        labState.setText("State:");
        panel.add(labState);
        
        txfState = new JTextField();
        txfState.setEditable(false);
        panel.add(txfState);
        
        labProduct = new JLabel();
        labProduct.setText("Product");
        panel.add(labProduct);
        
        txfProd = new JTextField();
        txfProd.setEditable(false);
        panel.add(txfProd);
        
        btnOK = new JButton();
        btnOK.setText("OK");
        btnOK.addActionListener(btnCtrl);
        panel.add(btnOK);
        
        setInfo();
        this.pack();
    }
    
    //Sets the info for the JTextFields 
    private void setInfo(){
        txfID.setText(Integer.toString(sub.getId()));
        txfName.setText(sub.getName());
        checkAndSetState();
        txfProd.setText(sub.getProduct().getName());
        
        
    }
    
    //Sets the info for the txfState
    private void checkAndSetState(){
        if(sub.getState() == State.DONE){
            txfState.setText("Done");
        }
        else if(sub.getState() == State.TREATMENT){
            txfState.setText("Treatment");
        }
        else if(sub.getState() == State.DRYING){
            txfState.setText("Drying");
        }
        else if(sub.getState() == State.WASTE){
            txfState.setText("Waste");
        }
    }
    
    //Allows to change the visibility of the dialog in the Controller
    private void thisSetVisible(boolean visiblity){
        this.setVisible(visiblity);
    }
    
    /*
     * Keeps track of the actions on the buttons
     */
    private class Controller implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            // TODO Auto-generated method stub
            if(ae.getSource().equals(btnOK)){
                thisSetVisible(false);
            }
        }
        
    }
}
package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

/**
 * @group Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 * @class WasteSubProduct
 * @author Lars Nielsen
 *
 */
public class WasteSubProduct extends JDialog{
    
    private Service service; //Holds a singelton of the service class
    
    //GUI Components
    private JPanel infoPanel, btnPanel;
    private JButton btnWaste;
    private JButton btnCan;
    private JLabel lblId;
    private JTextField txfID;
    private JLabel lblName;
    private JLabel lblNewLabel;
    private JLabel lblProduct;
    private JTextField txfName;
    private JTextField txfState;
    private JTextField txfProduct;
    
    private SubProduct sub; //Holds the SubProduct, which can be wasted
    
    
    private Controller btnCtrl; //Instance of the controller class
    
    
    public WasteSubProduct(SubProduct sub) {
        
        service = Service.getInstance(); 
        this.sub = sub;
        btnCtrl = new Controller();
        
        //Generel Prefernec for the Dialog
        setTitle("Waste Subproduct");
        setModal(true);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        //GUI Components and their preferens 
        infoPanel = new JPanel();
        getContentPane().add(infoPanel, BorderLayout.CENTER);
        GridBagLayout gbl_infoPanel = new GridBagLayout();
        gbl_infoPanel.columnWidths = new int[]{0, 0, 0};
        gbl_infoPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
        gbl_infoPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_infoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        infoPanel.setLayout(gbl_infoPanel);
        
        lblId = new JLabel("ID:");
        GridBagConstraints gbc_lblId = new GridBagConstraints();
        gbc_lblId.insets = new Insets(0, 0, 5, 5);
        gbc_lblId.anchor = GridBagConstraints.EAST;
        gbc_lblId.gridx = 0;
        gbc_lblId.gridy = 0;
        infoPanel.add(lblId, gbc_lblId);
        
        txfID = new JTextField();
        txfID.setEditable(false);
        GridBagConstraints gbc_txfID = new GridBagConstraints();
        gbc_txfID.insets = new Insets(0, 0, 5, 0);
        gbc_txfID.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfID.gridx = 1;
        gbc_txfID.gridy = 0;
        infoPanel.add(txfID, gbc_txfID);
        txfID.setColumns(10);
        
        lblName = new JLabel("NAME:");
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.anchor = GridBagConstraints.EAST;
        gbc_lblName.insets = new Insets(0, 0, 5, 5);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 1;
        infoPanel.add(lblName, gbc_lblName);
        
        txfName = new JTextField();
        txfName.setEditable(false);
        GridBagConstraints gbc_txfName = new GridBagConstraints();
        gbc_txfName.insets = new Insets(0, 0, 5, 0);
        gbc_txfName.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfName.gridx = 1;
        gbc_txfName.gridy = 1;
        infoPanel.add(txfName, gbc_txfName);
        txfName.setColumns(10);
        
        lblNewLabel = new JLabel("STATE:\n");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 2;
        infoPanel.add(lblNewLabel, gbc_lblNewLabel);
        
        txfState = new JTextField();
        txfState.setEditable(false);
        GridBagConstraints gbc_txfState = new GridBagConstraints();
        gbc_txfState.insets = new Insets(0, 0, 5, 0);
        gbc_txfState.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfState.gridx = 1;
        gbc_txfState.gridy = 2;
        infoPanel.add(txfState, gbc_txfState);
        txfState.setColumns(10);
        
        lblProduct = new JLabel("PRODUCT");
        GridBagConstraints gbc_lblProduct = new GridBagConstraints();
        gbc_lblProduct.anchor = GridBagConstraints.EAST;
        gbc_lblProduct.insets = new Insets(0, 0, 0, 5);
        gbc_lblProduct.gridx = 0;
        gbc_lblProduct.gridy = 3;
        infoPanel.add(lblProduct, gbc_lblProduct);
        
        txfProduct = new JTextField();
        GridBagConstraints gbc_txfProduct = new GridBagConstraints();
        gbc_txfProduct.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfProduct.gridx = 1;
        gbc_txfProduct.gridy = 3;
        infoPanel.add(txfProduct, gbc_txfProduct);
        txfProduct.setColumns(10);
        
        btnPanel = new JPanel();
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        btnPanel.setLayout(new GridLayout(1, 2, 0, 0));
        
        btnWaste = new JButton("Waste");
        btnWaste.addActionListener(btnCtrl);
        btnPanel.add(btnWaste);
        
        btnCan = new JButton("Cancel");
        btnCan.addActionListener(btnCtrl);
        btnPanel.add(btnCan);
        
        setInfo();
        checkAndSetState();
        
        this.pack();
    }
    
    /**
     * Allows to set the visibility of the dialog, in the Controller class 
     */
    private void desposeDialog(boolean despose){
        this.setVisible(despose);
    }
    
    /**
     * Sets the text of the JTextFields, so one know which SubProduct you are wasting 
     */
    private void setInfo(){
        txfID.setText(Integer.toString(sub.getId()));
        txfName.setText(sub.getName());
        checkAndSetState();
        txfProduct.setText(sub.getProduct().getName());
    }
    
    /**
     * Checks the state of the subproduct and sets the state of the txfState, to the current state
     */
    private void checkAndSetState(){
        if(sub.getState() == State.DONE){
            txfState.setText("Done");
        }
        else if(sub.getState() == State.TREATMENT){
            txfState.setText("Treatment");
        }
        else if(sub.getState() == State.DRYING){
            txfState.setText("Drying");
        }
        else if(sub.getState() == State.WASTE){
            txfState.setText("Waste");
        }
    }
    
    
    /**
     * 
     * The controller class, is controlling which actions is 
     *
     */
    private class Controller implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource().equals(btnWaste)){
                service.changeState(sub, State.WASTE);
                sub.setPosition(null);
                desposeDialog(false);
            }
            else if(ae.getSource().equals(btnCan)){
                desposeDialog(false);
            }
            
        }
        
    }

}
package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import carletti.gui.dialogs.CreateNewProductDialog;
import carletti.gui.dialogs.NewSubProductDialog;
import carletti.gui.dialogs.NextSubTreatmentDialog;
import carletti.gui.dialogs.ShowDoneDialog;
import carletti.gui.dialogs.ShowWastedDialog;
import carletti.gui.dialogs.ShowWastedDialogTableModel;
import carletti.gui.dialogs.SubProductDialog;
import carletti.gui.dialogs.WasteSubProduct;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.table.TableColumn;

/**
 * @group Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 * @class MainFrame
 * @author Lars Nielsen
 *
 */
public class MainFrame extends JFrame {
    
    private Service service; //Holds singelton instance of service

    //GUI components
    private JPanel buttonsPanel;
    private JScrollPane subProductsScrollPane;
    private JButton btnNewSubProduct;
    private JButton btnInfo;
    private JButton btnWaste;
    private JTable subProductTable;
    private NewSubProductTableModel subProductTableModel;
    private Dimension minimumSize = new Dimension(1000, 400);
    private Dimension btnMinSize = new Dimension(20, 180);
    private JButton btnNewProduct;
    private JButton btnProductInfo;
    private JButton btnTreatment;
    private JButton btnShowSubTreatment;
    private JButton btnShowDone;
    private JButton btnShowWasted;

    //Button controller
    private Controller btnCtrl;
    
    public MainFrame() {
        
        service = Service.getInstance();

        btnCtrl = new Controller();
        
        //Frame preferences 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        this.setTitle("Carletti");
        this.setSize(minimumSize);
        this.setLocation(new Point(100, 50));
        buttonsPanel = new JPanel();
        getContentPane().add(buttonsPanel, BorderLayout.EAST);

        //GUI Components preferences 
        btnInfo = new JButton("Info");
        btnInfo.setMinimumSize(btnMinSize);
        btnInfo.addActionListener(btnCtrl);

        btnWaste = new JButton("Waste");
        btnWaste.setMinimumSize(btnMinSize);
        btnWaste.addActionListener(btnCtrl);

        btnProductInfo = new JButton("Product Info");
        btnProductInfo.addActionListener(btnCtrl);

        btnNewSubProduct = new JButton("New subproduct");
        btnNewSubProduct.setMinimumSize(btnMinSize);
        btnNewSubProduct.addActionListener(btnCtrl);

        btnNewProduct = new JButton("New Product");
        
        btnTreatment = new JButton("Treatment");
        btnTreatment.addActionListener(btnCtrl);
        
        btnShowSubTreatment = new JButton("Show Subtreatment");
        btnShowSubTreatment.addActionListener(btnCtrl);
        
        btnShowDone = new JButton("Show Done");
        btnShowDone.addActionListener(btnCtrl);
        
        btnShowWasted = new JButton("Show Wasted");
        btnShowWasted.addActionListener(btnCtrl);
        
        GroupLayout gl_buttonsPanel = new GroupLayout(buttonsPanel);
        gl_buttonsPanel.setHorizontalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addComponent(btnInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnWaste, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnProductInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnNewProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnTreatment, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowSubTreatment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNewSubProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowDone, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowWasted, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );
        gl_buttonsPanel.setVerticalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_buttonsPanel.createSequentialGroup()
                    .addComponent(btnInfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnWaste, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnProductInfo)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewSubProduct, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewProduct)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowSubTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowDone)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowWasted)
                    .addGap(107))
        );
        buttonsPanel.setLayout(gl_buttonsPanel);
        btnNewProduct.addActionListener(btnCtrl);

        // subProductList = new JList();
        // subProductList.setListData(Service.getAllNotWastedSubProducts().toArray());
        // subProductList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        subProductTableModel = new NewSubProductTableModel();
        subProductTable = new JTable(subProductTableModel);
        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subProductTable.setDefaultRenderer(subProductTableModel.getColumnClass(0), new HighlighterCellRenderer());

        subProductsScrollPane = new JScrollPane(subProductTable);
        subProductsScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(subProductsScrollPane, BorderLayout.CENTER);

        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initColumnSizes(subProductTable);
        
        // --- Malik Lund ---
        Thread t = new Thread(new UpdaterThread(this));
        t.start();
        // ------------------

        this.setVisible(true);

    }
    
    /**
     * Hardcode some default column sizes.
     * @author Malik Lund
     * @param table
     */
    private void initColumnSizes(JTable table) {
        NewSubProductTableModel model = (NewSubProductTableModel)table.getModel();
        TableColumn column = null;
        for (int i = 0; i < model.getColumnCount(); i++){
            column = table.getColumnModel().getColumn(i);
            if (i == 1){
                column.setPreferredWidth(125);
            }
            else if (i == 2){
                column.setPreferredWidth(40);
            }
            else if (i == 3){
                column.setPreferredWidth(40);
            }
        }
    }

    /*
     * @author Malik Lund
     */
    public synchronized void updateList() {
        int selection = subProductTable.getSelectedRow();
        subProductTableModel.updateData();
        subProductTableModel.fireTableDataChanged();
        subProductTable.changeSelection(selection, 0, false, false);
    }

    private class Controller implements ActionListener {
        public void actionPerformed(ActionEvent ae) {

            /*
             * Info button
             */
            if (ae.getSource().equals(btnInfo)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                } else {
                    SubProductDialog spd = new SubProductDialog(sp);
                    spd.setLocationRelativeTo(MainFrame.this);
                    spd.setVisible(true);
                }
            }
            /*
             * New subproduct button
             */
            else if (ae.getSource().equals(btnNewSubProduct)) {
                NewSubProductDialog nspd = new NewSubProductDialog();
                nspd.setLocationRelativeTo(MainFrame.this);
                nspd.setVisible(true);
                updateList();
            } 
            /*
             * New product button
             */
            else if (ae.getSource().equals(btnNewProduct)) {
                CreateNewProductDialog newProductDialog = new CreateNewProductDialog();
                newProductDialog.setLocationRelativeTo(MainFrame.this);
                newProductDialog.setVisible(true);
            } 
            /*
             * Waste button
             */
            else if (ae.getSource().equals(btnWaste)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }

                else {
                    WasteSubProduct wsp = new WasteSubProduct(sp);
                    wsp.setLocationRelativeTo(MainFrame.this);
                    wsp.setVisible(true);
                    updateList();
                }

            }
            /*
             * Treatment button
             */
            else if (ae.getSource().equals(btnTreatment)){
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) 
                {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }
                else
                {
                    service.changeState(sp, State.TREATMENT);
                    sp.setPosition(null);
                }
                updateList();
            }
            /*
             * Product info button
             */
            else if (ae.getSource().equals(btnProductInfo)) {
                ShowProductFrame spf = new ShowProductFrame();
                spf.setLocationRelativeTo(MainFrame.this);
                spf.setVisible(true);
            }
            
            /*
             * Show subtreatment button
             */
            else if (ae.getSource().equals(btnShowSubTreatment))
            {
                NextSubTreatmentDialog ntd = new NextSubTreatmentDialog();
                ntd.setLocationRelativeTo(MainFrame.this);
                ntd.setVisible(true);
            }
            /*
             * Show done button
             */
            else if (ae.getSource().equals(btnShowDone))
            {
                ShowDoneDialog sdd = new ShowDoneDialog();
                sdd.setLocationRelativeTo(MainFrame.this);
                sdd.setVisible(true);
            }
            /*
             * Show wasted button
             */
            else if (ae.getSource().equals(btnShowWasted))
            {
                ShowWastedDialog swd = new ShowWastedDialog();
                swd.setLocationRelativeTo(MainFrame.this);
                swd.setVisible(true);
                
            }
        }
    }
    
    /**
     * Custom cell renderer that colors the "time remaining" cell
     * white, green, yellow or red depending on the time remaining
     * until max drying time is exceeded.
     * 
     * @author Malik Lund
     */
    private class HighlighterCellRenderer extends DefaultTableCellRenderer{
        private DefaultTableCellRenderer defaultRenderer;
        
        public HighlighterCellRenderer(){
            super();
            defaultRenderer = new DefaultTableCellRenderer();
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            SubProduct subProducts = subProductTableModel.getSubProduct(row);
            
            long currentTime = System.currentTimeMillis();
            long minTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMin();
            long optimalTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryPrime();
            long maxTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMax();
            
            // Set color
            if (minTime < currentTime && optimalTime > currentTime && column == 0){
                comp.setBackground(Color.green);
            } 
            else if (optimalTime < currentTime && maxTime > currentTime && column == 0){
                comp.setBackground(Color.yellow);
            }
            else if (currentTime > maxTime && column == 0) {
                comp.setBackground(Color.red);
            } else {
                // For reasons I don't understand and don't have time to look into
                // it is necessary to have two different renderers. The color change is
                // apparently persistent.
                return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
            
            return comp;
        }
    }
}
package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import carletti.gui.dialogs.CreateNewProductDialog;
import carletti.gui.dialogs.NewSubProductDialog;
import carletti.gui.dialogs.NextSubTreatmentDialog;
import carletti.gui.dialogs.ShowDoneDialog;
import carletti.gui.dialogs.ShowWastedDialog;
import carletti.gui.dialogs.ShowWastedDialogTableModel;
import carletti.gui.dialogs.SubProductDialog;
import carletti.gui.dialogs.WasteSubProduct;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import javax.swing.table.TableColumn;


public class MainFrameTabbed extends JFrame {
    private Service service;

    private JPanel buttonsPanel;
    private JScrollPane subProductsScrollPane, subProductsTreatmentScrollPane;
    private JButton btnNewSubProduct;
    private JButton btnInfo;
    private JButton btnWaste;
    private JTabbedPane tabbedPane;
    private JTable subProductTable, subProductsTreatmentTable;
    private NewSubProductTableModelTabbed subProductTableModel, subProductsTreatmentTableModel;

    private Controller btnCtrl;

    private Dimension minimumSize = new Dimension(1000, 400);
    private Dimension btnMinSize = new Dimension(20, 180);
    private JButton btnNewProduct;
    private JButton btnProductInfo;
    private JButton btnTreatment;
    private JButton btnShowSubTreatment;
    private JButton btnShowDone;
    private JButton btnShowWasted;

    public MainFrameTabbed() {
        service = Service.getInstance();

        btnCtrl = new Controller();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        this.setTitle("Carletti");
        this.setSize(minimumSize);
        this.setLocation(new Point(100, 50));
        buttonsPanel = new JPanel();
        getContentPane().add(buttonsPanel, BorderLayout.EAST);

        btnInfo = new JButton("Info");
        btnInfo.setMinimumSize(btnMinSize);
        btnInfo.addActionListener(btnCtrl);

        btnWaste = new JButton("Waste");
        btnWaste.setMinimumSize(btnMinSize);
        btnWaste.addActionListener(btnCtrl);

        btnProductInfo = new JButton("Product Info");
        btnProductInfo.addActionListener(btnCtrl);

        btnNewSubProduct = new JButton("New subproduct");
        btnNewSubProduct.setMinimumSize(btnMinSize);
        btnNewSubProduct.addActionListener(btnCtrl);

        btnNewProduct = new JButton("New Product");
        
        btnTreatment = new JButton("Treatment");
        btnTreatment.addActionListener(btnCtrl);
        
        btnShowSubTreatment = new JButton("Show Subtreatment");
        btnShowSubTreatment.addActionListener(btnCtrl);
        
        btnShowDone = new JButton("Show Done");
        btnShowDone.addActionListener(btnCtrl);
        
        btnShowWasted = new JButton("Show Wasted");
        btnShowWasted.addActionListener(btnCtrl);
        
        GroupLayout gl_buttonsPanel = new GroupLayout(buttonsPanel);
        gl_buttonsPanel.setHorizontalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addComponent(btnInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnWaste, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnProductInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnNewProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnTreatment, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowSubTreatment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNewSubProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowDone, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowWasted, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );
        gl_buttonsPanel.setVerticalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_buttonsPanel.createSequentialGroup()
                    .addComponent(btnInfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnWaste, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnProductInfo)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewSubProduct, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewProduct)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowSubTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowDone)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowWasted)
                    .addGap(107))
        );
        buttonsPanel.setLayout(gl_buttonsPanel);
        btnNewProduct.addActionListener(btnCtrl);
        
        tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // --- Drying table ---
        subProductTableModel = new NewSubProductTableModelTabbed(State.DRYING);
        subProductTable = new JTable(subProductTableModel);
        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subProductTable.setDefaultRenderer(subProductTableModel.getColumnClass(0), new HighlighterCellRenderer());

        subProductsScrollPane = new JScrollPane(subProductTable);
        subProductsScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//      getContentPane().add(subProductsScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Drying", subProductsScrollPane);

        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initColumnSizes(subProductTable);
        
        // --- In treatment table ---
        subProductsTreatmentTableModel = new NewSubProductTableModelTabbed(State.TREATMENT);
        subProductsTreatmentTable = new JTable(subProductsTreatmentTableModel);
        subProductsTreatmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subProductsTreatmentTable.setDefaultRenderer(subProductTableModel.getColumnClass(0), new HighlighterCellRenderer());
        
        subProductsTreatmentScrollPane = new JScrollPane(subProductsTreatmentTable);
        subProductsTreatmentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        tabbedPane.addTab("In treatment", subProductsTreatmentScrollPane);
        
        // --- Malik Lund start---
        Thread t = new Thread(new UpdaterThreadForTabbedMain(this));
        t.start();
        // -------------- end ----

        this.setVisible(true);

    }
    
    /**
     * Hardcode some default column sizes.
     * @author Malik Lund
     * @param table
     */
    private void initColumnSizes(JTable table) {
        NewSubProductTableModelTabbed model = (NewSubProductTableModelTabbed)table.getModel();
        TableColumn column = null;
        for (int i = 0; i < model.getColumnCount(); i++){
            column = table.getColumnModel().getColumn(i);
            if (i == 1){
                column.setPreferredWidth(125);
            }
            else if (i == 2){
                column.setPreferredWidth(40);
            }
            else if (i == 3){
                column.setPreferredWidth(40);
            }
        }
    }

    /*
     * @author Malik Lund
     */
    public synchronized void updateList() {
        int selection = subProductTable.getSelectedRow();
        subProductTableModel.updateData();
        subProductTableModel.fireTableDataChanged();
        subProductTable.changeSelection(selection, 0, false, false);
        
        updateTable(subProductsTreatmentTable);
    }
    
    private synchronized void updateTable(JTable table){
        int selection = table.getSelectedRow();
        NewSubProductTableModelTabbed tableModel = (NewSubProductTableModelTabbed) table.getModel();
        tableModel.updateData();
        tableModel.fireTableDataChanged();
        if (selection >= 0 && selection < tableModel.getColumnCount()){
            table.changeSelection(selection, 0, false, false);
        }
    }

    private class Controller implements ActionListener {
        public void actionPerformed(ActionEvent ae) {

            if (ae.getSource().equals(btnInfo)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                } else {
                    SubProductDialog spd = new SubProductDialog(sp);
                    spd.setLocationRelativeTo(MainFrameTabbed.this);
                    spd.setVisible(true);
                }
            } else if (ae.getSource().equals(btnNewSubProduct)) {
                NewSubProductDialog nspd = new NewSubProductDialog();
                nspd.setLocationRelativeTo(MainFrameTabbed.this);
                nspd.setVisible(true);
                updateList();
            } else if (ae.getSource().equals(btnNewProduct)) {
                CreateNewProductDialog newProductDialog = new CreateNewProductDialog();
                newProductDialog.setLocationRelativeTo(MainFrameTabbed.this);
                newProductDialog.setVisible(true);
            } else if (ae.getSource().equals(btnWaste)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }

                else {
                    WasteSubProduct wsp = new WasteSubProduct(sp);
                    wsp.setLocationRelativeTo(MainFrameTabbed.this);
                    wsp.setVisible(true);
                    updateList();
                }

            }
            
            else if (ae.getSource().equals(btnTreatment)){
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) 
                {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }
                else
                {
                    service.changeState(sp, State.TREATMENT);
                }
                updateList();
            }

            else if (ae.getSource().equals(btnProductInfo)) {
                ShowProductFrame spf = new ShowProductFrame();
                spf.setLocationRelativeTo(MainFrameTabbed.this);
                spf.setVisible(true);
            }
            
            else if (ae.getSource().equals(btnShowSubTreatment))
            {
                NextSubTreatmentDialog ntd = new NextSubTreatmentDialog();
                ntd.setLocationRelativeTo(MainFrameTabbed.this);
                ntd.setVisible(true);
            }
            else if (ae.getSource().equals(btnShowDone))
            {
                ShowDoneDialog sdd = new ShowDoneDialog();
                sdd.setLocationRelativeTo(MainFrameTabbed.this);
                sdd.setVisible(true);
            }
            else if (ae.getSource().equals(btnShowWasted))
            {
                ShowWastedDialog swd = new ShowWastedDialog();
                swd.setLocationRelativeTo(MainFrameTabbed.this);
                swd.setVisible(true);
                
            }
        }
    }
    
    /**
     * Custom cell renderer that colors the "time remaining" cell
     * white, green, yellow or red depending on the time remaining
     * until max drying time is exceeded.
     * 
     * @author Malik Lund
     */
    private class HighlighterCellRenderer extends DefaultTableCellRenderer{
        private DefaultTableCellRenderer defaultRenderer;
        
        public HighlighterCellRenderer(){
            super();
            defaultRenderer = new DefaultTableCellRenderer();
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            SubProduct subProducts = subProductTableModel.getSubProduct(row);
            
            long currentTime = System.currentTimeMillis();
            long minTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMin();
            long optimalTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryPrime();
            long maxTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMax();
            
            // Set color
            if (minTime < currentTime && optimalTime > currentTime && column == 0){
                comp.setBackground(Color.green);
            } 
            else if (optimalTime < currentTime && maxTime > currentTime && column == 0){
                comp.setBackground(Color.yellow);
            }
            else if (currentTime > maxTime && column == 0) {
                comp.setBackground(Color.red);
            } else {
                // For reasons I don't understand and don't have time to look into
                // it is necessary to have two different renderers. The color change is
                // apparently persistent.
                return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
            
            return comp;
        }
    }
}
/**
 * @group Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bungaard
 * @class NewSubProductTableModel
 * @author Lars Nielsen
 */
package carletti.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.dao.JpaDao;
import carletti.model.LongToStringParser;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class NewSubProductTableModel extends AbstractTableModel{
    private Service service;
    // Column headers.
    private String[] coloumnNames = {"Time left", "Started on", "ID", "Position", "Name", "State", "Subtreatment", "Subtreatment #"};
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State state = State.DRYING;
        
    public NewSubProductTableModel(){
        service = Service.getInstance();
        newData = service.getSubProducts(state);
    }

    @Override
    public int getColumnCount() {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount() {
        return newData.size();
    }

    @Override
    public String getColumnName(int col){
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        SubProduct sp = newData.get(row);
        Object[] value = {
                LongToStringParser.parseLongToString(sp.timeLeft()), sp.getTime(sp.getTimeAdded()), 
                new Integer(sp.getId()), sp.getPosition().getPosID(), sp.getName(), 
                sp.getState(), sp.getCurrentSubTreatment().getName(), 
                (sp.getCurrentSubTreatmentIndex() + 1) + "/" + sp.getSubtreatments().size() 
        };
        return value[col];
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
    
    public SubProduct selctedSubProduct(int selectedRow){
        if(selectedRow >= 0){
            return newData.get(selectedRow);
        }
        else{
            return null;
        }
        
    }
    
    /**
     * @author Malik Lund
     * @return
     */
    public SubProduct getSubProduct(int row){
        return newData.get(row);
    }

    public void updateData() {
        newData = service.getSubProducts(state);
    }
    

}
/**
 * @group Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bungaard
 * @class NewSubProductTableModel
 * @author Lars Nielsen
 */
package carletti.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.dao.JpaDao;
import carletti.model.LongToStringParser;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class NewSubProductTableModelTabbed extends AbstractTableModel{
    private Service service;
    // Column headers.
    private String[] coloumnNames = {"Time left", "Started on", "ID", "Position", "Name", "State", "Subtreatment", "Subtreatment #"};
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State state;
        
    public NewSubProductTableModelTabbed(State state){
        service = Service.getInstance();
        this.state = state;
        newData = service.getSubProducts(state);
    }

    @Override
    public int getColumnCount() {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount() {
        return newData.size();
    }

    @Override
    public String getColumnName(int col){
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        SubProduct sp = newData.get(row);
        Object[] value = {
                LongToStringParser.parseLongToString(sp.timeLeft()), sp.getTime(sp.getTimeAdded()), 
                new Integer(sp.getId()), sp.getPosition().getPosID(), sp.getName(), 
                sp.getState(), sp.getCurrentSubTreatment().getName(), 
                (sp.getCurrentSubTreatmentIndex() + 1) + "/" + sp.getSubtreatments().size() 
        };
        return value[col];
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
    
    public SubProduct selctedSubProduct(int selectedRow){
        if(selectedRow >= 0){
            return newData.get(selectedRow);
        }
        else{
            return null;
        }
        
    }
    
    /**
     * @author Malik Lund
     * @return
     */
    public SubProduct getSubProduct(int row){
        return newData.get(row);
    }

    public void updateData() {
        newData = service.getSubProducts(state);
    }
    

}
package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import carletti.service.Service;
import carletti.dao.JpaDao;

public class ShowProductFrame extends JDialog
{
    private Service service;
    private JPanel contentPane;
    private JPanel panel;
    private JButton btnOk;
    private JSplitPane splitPane;
    private JTextPane textPane;
    private JScrollPane scrollPane;
    private JScrollPane screooPane2;
    private JList list;
    private Dimension minimumSize = new Dimension(400, 500);
    
    private Controller btnCtrl;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    ShowProductFrame frame = new ShowProductFrame();
                    frame.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ShowProductFrame()
    {
        service = Service.getInstance();
        
        btnCtrl = new Controller();
        this.setMinimumSize(minimumSize);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);
        
        btnOk = new JButton("OK");
        panel.add(btnOk);
        btnOk.addActionListener(btnCtrl);
        
        splitPane = new JSplitPane();
        contentPane.add(splitPane, BorderLayout.CENTER);
        
        textPane = new JTextPane();
        textPane.setEditable(false);
        splitPane.setRightComponent(textPane);
        
        list = new JList();
        contentPane.add(list, BorderLayout.NORTH);
        list.setListData(service.getProducts().toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                        
                if (e.getValueIsAdjusting())
                    updateTextPane();
            }
            
        });
        
        scrollPane = new JScrollPane(list);
        splitPane.setLeftComponent(scrollPane);
    
        screooPane2 = new JScrollPane(textPane);
        splitPane.setRightComponent(screooPane2);
        
    }
    
    public void updateTextPane()
    {
        int index = list.getSelectedIndex();
        if (index >= 0)
            textPane.setText(service.getProducts().get(list.getSelectedIndex()).getName() + "\n" +
                    service.getProducts().get(list.getSelectedIndex()).getDescription() + "\n" + "Treatment:" + "\n" +
                    service.getProducts().get(list.getSelectedIndex()).getTreatment());
    }
    
    private class Controller implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            
            if(ae.getSource().equals(btnOk)){
                ShowProductFrame.this.setVisible(false);
                }
        }
    }
}
package carletti.gui;

import carletti.gui.dialogs.NextSubTreatmentDialog;

/**
 * Continuously updates the list of SubProducts in MainFrame
 * Obs. keeps running until window is closed.
 * @author Malik Lund
 *
 */
public class UpdaterThread implements Runnable {
    private MainFrame mainFrame;
    private NextSubTreatmentDialog nextSubTreatmentDialog;
    
    public UpdaterThread(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }
    
    public UpdaterThread(NextSubTreatmentDialog nextSubTreatmentDialog)
    {
        this.nextSubTreatmentDialog =  nextSubTreatmentDialog;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true){
            mainFrame.updateList();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
package carletti.gui;

import carletti.gui.dialogs.NextSubTreatmentDialog;

/**
 * Continuously updates the list of SubProducts in MainFrame
 * Obs. keeps running until window is closed.
 * @author Malik Lund
 *
 */
public class UpdaterThreadForTabbedMain implements Runnable {
    private MainFrameTabbed mainFrame;
    private NextSubTreatmentDialog nextSubTreatmentDialog;
    
    public UpdaterThreadForTabbedMain(MainFrameTabbed mainFrame){
        this.mainFrame = mainFrame;
    }
    
    public UpdaterThreadForTabbedMain(NextSubTreatmentDialog nextSubTreatmentDialog)
    {
        this.nextSubTreatmentDialog =  nextSubTreatmentDialog;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true){
            mainFrame.updateList();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
package carletti.model;

/**
 * 
 * @author Malik Lund
 *
 */
public class LongToStringParser {
    
    /**
     * Converts a time given in milliseconds into a string
     * of the form [+/-]DDdHHhMMmSSs where DD represents days, HH represents hours,
     * MM represents minutes and SS represents seconds. 
     * All numbers get padded with a zero if they only consist of a single digit.
     * @param time
     * @return
     */
    public static String parseLongToString(long time){
        boolean negative = false;
        int days, hours, minutes, seconds;
        
        if (time < 0){
            negative = true;
            time = - time;
        }
        
        days = (int) (time / (1000 * 60 * 60 * 24));
        time = time - days * 1000 * 60 * 60 * 24;
        
        hours = (int) (time / (1000 * 60 * 60));
        time = time - hours * 1000 * 60 * 60;
        
        minutes = (int) (time / (1000 * 60));
        time = time - minutes * 1000 * 60;
        
        seconds = (int) (time / (1000));
        
        StringBuffer buffer = new StringBuffer();
        if (negative){
            buffer.append("+");
        } else {
            buffer.append("-");
        }
        buffer.append(padNumber(days) + "d");
        buffer.append(padNumber(hours) + "h");
        buffer.append(padNumber(minutes) + "m");
        buffer.append(padNumber(seconds) + "s");
        
        return buffer.toString();
    }
    
    /**
     * Pads a number with zero if it is below 10
     * @param number
     * @return
     */
    private static String padNumber(int number){
        if (number < 10){
            return "0" + number;
        } else {
            return number + "";
        }
    }
}

package carletti.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
/**
 *@group Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bundgaard
 *@class Position
 *@author Lars Nielsen
 */
public class Position {

    @Id
    @GeneratedValue
    private int id;
    private String posID;
    @OneToOne(mappedBy = "position")
    private SubProduct sp; // Holds which product is on the position
    public Position(String posID) {
        this.posID = posID;
    }
    
    public Position()
    {
        
    }

    public String getPosID() {
        return posID;
    }

    public void setPosID(String posID) {
        this.posID = posID;
    }
    
    public SubProduct getSubProduct(){
        return sp;
    }

    
    /*
     * @precon There can't be removed a SubProduct from a position, where there
     * are non located
     */
    public boolean removeSubProductFromPosition() {
        if (sp == null) {
            return false;
        } else {
            sp = null;
            return true;
        }
    }
    
    /*
     * @precon There can't be put a SubProduct on a position where there already
     * is another SubProduct already position
     */
    public boolean putSubProductOnPosition(SubProduct sp) {
        if (this.sp == null) {
            putSubProductOnPositionUD(sp);
            sp.setPositionUD(this);
            return true;
        } else {
            return false;
        }
    }

    
    void putSubProductOnPositionUD(SubProduct sp){
        this.sp = sp;
    }
    
    public String toString(){
        return getPosID();
    }
    
    
}
package carletti.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 * Product encapsulates the name,
 * description and treatment of each sub-product.
 * 
 * @author Malik Lund
 *
 */
@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    @OneToOne(cascade={CascadeType.PERSIST})
    private Treatment treatment;
    
    /**
     * Create a Product-object. Id is generated automatically.
     * @param name A short name for the product.
     * @param description A description of the product.
     * @param treatment A treatment plan for the product.
     */
    public Product(String name, String description, Treatment treatment) {
        this.name = name;
        this.description = description;
        this.treatment = treatment;
    }
    
    /**
     * Empty constructor for JPA.
     */
    public Product()
    {
        
    }

    /**
     * Get the treatment associated with this product
     * type.
     * @return The Treatment object associated with this product type.
     */
    public Treatment getTreatment() {
        return treatment;
    }

    /**
     * Replace the treatment associated with this product
     * type.
     * @param treatment The new Treatment object to be associated with this product type.
     */
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    /**
     * Get the id given by the database.
     * @return This objects id represented by an int value.
     */
    public int getId(){
        return id;
    }
    
    /**
     * Get the name of this product.
     * @return A String object containing the name.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Replace the name of this product.
     * @param newName The new name as a String object.
     */
    public void setName(String newName){
        this.name = newName;
    }
    
    /**
     * Get a description of the product.
     * @return The description of the product as a String.
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Replace the description of this product.
     * @param newDescription A String object that is to be the new description.
     */
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
    
//  Added by Martin
    public String toString()
    {
        return name;
    }
//
}
package carletti.model;
/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bundgaard
 * @author: Lars Nielsen
 * @enum-class: State
 */
public enum State { TREATMENT, DRYING, DONE, WASTE}
package carletti.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bundgaard
 * @author: Lars Nielsen
 * @class: SubProduct
 */
public class SubProduct implements Comparable<SubProduct> {

    @Id
    @GeneratedValue
    private int id; // To have a key for the database
    private String name;
    @Enumerated(EnumType.STRING)
    private State state; // The current state which a SubProduct is in

    @OneToMany
    private List<SubTreatment> subtreatments = new ArrayList<SubTreatment>();
    private int currentSubTreatmentIndex = 0;
    @OneToOne(cascade = { CascadeType.PERSIST })
    private Product product;
    private long timeAdded;
    @OneToOne(cascade={CascadeType.PERSIST})
    private Position position;

    public SubProduct(String name, Product product, long timeAdded,
            Position position) {

        this.name = name;
        this.state = State.DRYING;
        this.setProduct(product);
        this.timeAdded = timeAdded;
        this.setPosition(position);
    }

    public SubProduct() {

    }

    public long getTimeAdded() {
        return timeAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<SubTreatment> getSubtreatments() {
        return new ArrayList<SubTreatment>(subtreatments);
    }

    private void setSubtreatments(List<SubTreatment> subtreatments) {
        this.subtreatments = subtreatments;
        setCurrentSubTreatmentIndex(0);
    }

    public int getCurrentSubTreatmentIndex() {
        return currentSubTreatmentIndex;
    }

    public void setCurrentSubTreatmentIndex(int currentSubTreatmentIndex) {
        this.currentSubTreatmentIndex = currentSubTreatmentIndex;
    }

    public Product getProduct() {
        return product;
    }

    private void setProduct(Product product) {
        this.product = product;
        setSubtreatments(product.getTreatment().getSubTreatments());
    }

    /*
     * @post subtreatments is in sort by need
     */
    public void nextSubTreatment() {
        if (currentSubTreatmentIndex < (subtreatments.size() - 1)) {
            setCurrentSubTreatmentIndex(getCurrentSubTreatmentIndex() + 1);
            setState(State.DRYING); // Martin
        } else if (currentSubTreatmentIndex >= (subtreatments.size() - 1)) {
            setState(State.DONE);
        }
    }

    public SubTreatment getCurrentSubTreatment() {
        return subtreatments.get(getCurrentSubTreatmentIndex());
    }

    public String getTime(long time) {
        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy '-' HH:mm:ss");
        return sd.format(time);

    }

    /**
     * @author Lars
     * @return
     */

    public Position getPosition() {
        return position;
    }

    void setPositionUD(Position position) {
        this.position = position;
    }

    public void setPosition(Position position) {
        if (position == null){
            this.position.putSubProductOnPositionUD(null);
        } else {
            position.putSubProductOnPositionUD(this);
        }
        setPositionUD(position);
    }

    /**
     * 
     * @author Martin
     */
    @Override
    public String toString() {
        return LongToStringParser.parseLongToString(timeLeft()) + " | " + getTime(timeAdded) + " | "
                + getId() + " " + name + " " + getState() + " " + (getCurrentSubTreatmentIndex() + 1)
                + "" + " / " + getSubtreatments().size();
    }

    /**
     * 
     * @author Martin
     * @return
     */
    public long timeLeft() {
        long max = (getCurrentSubTreatment().getDryMax() + timeAdded - System
                .currentTimeMillis());

        return max;
    }

    /** 
     * 
     * @author Martin
     */
    
//  @Override
//  public int compareTo(SubProduct otherSubProduct) {
//      return (int) (timeLeft() - otherSubProduct.timeLeft());
//  }
    
    @Override
    public int compareTo(SubProduct otherSupProduct)
    {
        long l = (timeLeft() - otherSupProduct.timeLeft());
    
        int result = 0;
        
        if (l < 0)
            result = -1; 
        
        else if (l > 0)
            result = 1;
     
        return result;
        
    }
}
package carletti.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bundgaard
 * @author: Lars Nielsen
 * @class: SubTreatment
 */
public class SubTreatment {
    
    /*
     * Holds the drying time for minimum, prime and maximum drying time
     */
    @Id
    @GeneratedValue
    private int id; //Is need for the JPA identification 
    private long dryMin, dryPrime, dryMax;
    private String name;
    
    SubTreatment(String name, long dryMin, long dryPrime, long dryMax){
        this.setName(name);
        this.dryMin = dryMin;
        this.dryPrime = dryPrime;
        this.dryMax = dryMax;
        
    }
    
    public SubTreatment()
    {
        
    }

    public long getDryMin() {
        return dryMin;
    }

    public void setDryMin(long dryMin) {
        this.dryMin = dryMin;
    }

    public long getDryPrime() {
        return dryPrime;
    }

    public void setDryPrime(long dryPrime) {
        this.dryPrime = dryPrime;
    }

    public long getDryMax() {
        return dryMax;
    }

    public void setDryMax(long dryMax) {
        this.dryMax = dryMax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString()
    {
        return "\n" + name + "\n" + "min= " + getDryMin() + "\nprime= " + getDryPrime() + "\nmax= " + getDryMax();
    }
}
package carletti.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;



@Entity
/**
 * Treatment represents the various drying treatments a product 
 * needs to complete before it is a finished product.
 * 
 * @author Malik Lund
 *
 */
public class Treatment {
    
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private List<SubTreatment> subTreatments;
    
    /**
     * 
     * @param name
     */
    
    public Treatment()
    {
        
    }
    
    public Treatment(String name){
        this.name = name;
        subTreatments = new ArrayList<SubTreatment>();
    }
    
    /**
     * Creates and appends a new treatment to the list.
     * @param name The name of the sub-treatment.
     * @param dryMin Minimum drying time.
     * @param dryPrime Optimal or prime drying time.
     * @param dryMax Maximal drying time.
     */
    public void createSubTreatment(String name, long dryMin, long dryPrime, long dryMax){
        SubTreatment st = new SubTreatment(name, dryMin, dryPrime, dryMax);
        subTreatments.add(st);
    }
    
    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }

    /**
     * OBS: Altering the returned list has no effect on the
     *      internal list (though altering each object in the
     *      list have an effect).
     * @return A new List.
     */
    public List<SubTreatment> getSubTreatments() {
        return new ArrayList<SubTreatment>(subTreatments);
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString()
    {
        return name + "\n" + getSubTreatments().toString() + "\n";
    }
}
package carletti.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import carletti.dao.Dao;
import carletti.dao.LocalDao;
import carletti.model.LongToStringParser;
import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * 
 * @author Martin
 * 
 */

public class Service
{

    private Dao dao;
    private static Service serviceInstance;

    private Service(Dao dao)
    {
        this.dao = dao;
    }

    /**
     * 
     * @param dao
     *            The dao to use. OBS: After initial call, dao doesn't change!
     * @return The instance
     * @author Malik Lund
     */
    public static Service getInstance(Dao dao)
    {
        if (serviceInstance == null)
        {
            serviceInstance = new Service(dao);
        }
        return serviceInstance;
    }

    /**
     * Simple getInstance method that defaults to a local, impersistent storage
     * in memory. If a call has been made to getInstance(Dao) an instance with
     * the previously selected Dao is returned.
     * 
     * @return
     */
    public static Service getInstance()
    {
        if (serviceInstance == null)
        {
            serviceInstance = new Service(LocalDao.getInstance());
        }
        return serviceInstance;
    }

    /**
     * 
     * @param name
     *            The name of the sup-product.
     * @param product
     *            The product of the sub-product.
     * @param position
     *            The position of the sub-product.
     * @return Sub-product.
     */
    public SubProduct createSubProduct(String name, Product product,
            Position position)
    {
        SubProduct sp = new SubProduct(name, product,
                System.currentTimeMillis(), position); // TODO Make timestamp an
                                                        // option to the user
        dao.storeSubProduct(sp);

        return sp;
    }

    /**
     * Remove sub-product
     * 
     * @param subProduct
     *            The sub-product that is to be removed.
     */
    public void removeSubproduct(SubProduct subProduct)
    {
        dao.removeSubProduct(subProduct);
    }

    /**
     * Waste sub-product
     * 
     * @param subProduct
     *            The sub-product that is to be wasted
     */
    public void discardSubProduct(SubProduct subProduct)
    {
        State waste = State.WASTE;
        dao.changeStateOfSubProduct(subProduct, waste);
    }

    /**
     * Get sub-product
     * 
     * @param subProduct
     *            The sub-product to be returned.
     * @return Sub-product.
     */
    public SubProduct getSubproduct(SubProduct subProduct)
    {

        int i = dao.getSubProducts().indexOf(subProduct);
        if (i >= 0)
        {
            return dao.getSubProducts().get(i);
        } else
        {
            return null;
        }
    }

    /**
     * 
     * @author Malik Lund
     * @return
     */
    public List<Treatment> getTreatments()
    {
        return dao.getTreatments();
    }

    /**
     * Create treatment.
     * 
     * @param name
     *            The name of the treatment
     * @return Treatment
     */
    public Treatment createTreatment(String name)
    {
        Treatment t = new Treatment(name);
        return t;
    }

    /**
     * remove treatment
     * 
     * @param treatment
     *            The treatment to be removed.
     */
    public void removeTreatment(Treatment treatment)
    {
        dao.removeTreatment(treatment);
    }

    /**
     * Create new Product.
     * 
     * @param name
     *            The name of the product.
     * @param description
     *            A description of the product.
     * @param treatment
     *            The treatment for the product.
     * @return Product.
     */
    public Product createProduct(String name, String description,
            Treatment treatment)
    {
        Product p = new Product(name, description, treatment);
        dao.storeProduct(p);
        return p;
    }

    /**
     * Remove product
     * 
     * @param product
     *            The product to be removed.
     */
    public void removeProduct(Product product)
    {
        dao.removeProduct(product);
    }

    /**
     * Get product.
     * @param product The product to be returned
     * @return Product.
     */
    public Product getProduct(Product product)
    {
        int i = dao.getProducts().indexOf(product);
        if (i >= 0)
        {
            return dao.getProducts().get(i);
        } else
        {
            return null;
        }

    }
    
    /**
     * Next treatment.
     * @param subProduct The sub-product to be sent to the next treatment
     */
    public void nextTreatnemt(SubProduct subProduct)
    {
        if (subProduct.getCurrentSubTreatmentIndex() < (subProduct
                .getSubtreatments().size() - 1))
        {
            subProduct.setCurrentSubTreatmentIndex(subProduct
                    .getCurrentSubTreatmentIndex() + 1);
            dao.changeStateOfSubProduct(subProduct, State.DRYING); // Martin
        } else if (subProduct.getCurrentSubTreatmentIndex() >= (subProduct
                .getSubtreatments().size() - 1))
        {
            dao.changeStateOfSubProduct(subProduct, State.DONE);
        }
    }

    /**
     * Set product done.
     * @param subProduct The subproduct of which the state is to change to done.
     */
    public void subProductDone(SubProduct subProduct)
    {
        State done = State.DONE;
        dao.changeStateOfSubProduct(subProduct, done);
    }

    /**
     * Gets list of all done products.
     * @return
     */
//  public List<SubProduct> showAllDoneProduct()
//  {
//      State done = State.DONE;
//
//      return dao.getSubProducts(done);
//  }
//
    public List<Product> getProducts()
    {
        return dao.getProducts();
    }
//
//  public List<SubProduct> showAllSubProduct()
//  {
//      List<SubProduct> list = dao.getSubProducts();
//
//      Collections.sort(list);
//      return list;
//  }
//
//  public String getInfoAboutSubProduct(SubProduct subProduct)
//  {
//      int i = dao.getSubProducts().indexOf(subProduct);
//      return dao.getSubProducts().get(i) + "";
//  }
//
//  public List<SubProduct> getDryingSubProduct()
//  {
//      State drying = State.DRYING;
//      return dao.getSubProducts(drying);
//  }

    // --------- Malik-------------
    public void createSomeObjects()
    {
        Service s = Service.getInstance();

        s.createPosition("A1");
        s.createPosition("A2");
        s.createPosition("A3");
        s.createPosition("B1");
        s.createPosition("B2");
        s.createPosition("B3");
        s.createPosition("C1");
        s.createPosition("C2");
        s.createPosition("C3");

        Treatment t1 = s.createTreatment("Red chocolate MMs");
        t1.createSubTreatment("1st drying", generateTime(2), generateTime(30),
                generateTime(0, 1));
        t1.createSubTreatment("2nd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        t1.createSubTreatment("3rd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        Product p1 = s.createProduct("Red Chocolate MMs",
                "Info about red chocolate MMs", t1);

        Treatment t2 = s.createTreatment("Liquorice");
        t2.createSubTreatment("1st drying", generateTime(0, 15),
                generateTime(0, 20), generateTime(0, 25));
        t2.createSubTreatment("2nd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        Product p2 = s.createProduct("Liquorice",
                "Liquorice with coloured sugar layer", t2);

        Treatment t3 = s.createTreatment("Coffeebean");
        t3.createSubTreatment("1st drying", generateTime(0, 55),
                generateTime(0, 60), generateTime(0, 10, 1));
        t3.createSubTreatment("2nd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        t3.createSubTreatment("3rd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        Product p3 = s.createProduct("Coffee Bean",
                "Coffee paste with a layer of chocolate", t3);

        List<Position> positions = s.getPositions();

        SubProduct sp1 = s.createSubProduct("Foobar", p1, positions.get(0));
        SubProduct sp2 = s.createSubProduct("Barbaz", p2, positions.get(4));
        SubProduct sp3 = s.createSubProduct("Bazfoo", p3, positions.get(8));
    }

    /**
     * Takes a variable number of arguments and generates a time in
     * milliseconds.
     * 
     * The first value represents seconds, the second minutes, the third hours
     * and the fourth hours. Any arguments after that are ignored.
     * 
     * @author Malik Lund
     * @param times
     * @return
     */
    private long generateTime(int... times)
    {
        long time = 0;
        long[] constants =
        { 1000, 1000 * 60, 1000 * 60 * 60, 1000 * 60 * 60 * 24 };
        int roof = Math.min(times.length, 4);
        for (int i = 0; i < roof; i++)
        {
            time += times[i] * constants[i];
        }
        return time;
    }

    // ----------------------------

    
    //--------Martin---------------
    /**
     * Get a list of all sub-product with the given state. 
     * @param state The state for the product to be returned.
     * @return list of products.
     */
    public List<SubProduct> getSubProducts(State state)
    {
        ArrayList<SubProduct> subProduct = new ArrayList<SubProduct>();
        for (int i = 0; i < dao.getSubProducts().size(); i++)
        {
            if (dao.getSubProducts().get(i).getState() == state)
            {
                subProduct.add(dao.getSubProducts().get(i));
            }
        }
        Collections.sort(subProduct);
        return subProduct;
    }
    //--------------------------------

    
    // -----Lars
    public List<Position> getPositions()
    {
        return dao.getPositions();
    }

    public void createPosition(String posID)
    {
        dao.storePosition(new Position(posID));

    }

    // -----

    /**
     * 
     * @param sp
     *            The product of which the state id to change.
     * @param state
     *            The new state of the product.
     */
    public void changeState(SubProduct sp, State state)
    {
        dao.changeStateOfSubProduct(sp, state);
    }
}
package carletti.test.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * @author Malik
 *
 */
public class DaoTest {
    Dao dao = JpaDao.getInstance();

    @Before
    public void setUp() throws Exception {
        
    }
    
    @After
    /**
     * Delete everything before starting new test.
     */
    public void cleanUp(){
        for (SubProduct sp: dao.getSubProducts()){
            dao.removeSubProduct(sp);
        }
        
        for(Product p: dao.getProducts()){
            dao.removeProduct(p);
        }
        
        for (Treatment t: dao.getTreatments()){
            dao.removeTreatment(t);
        }
    }

    @Test
    public void storeTreatmentTest() {
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        dao.storeTreatment(t1);
        List<Treatment> treatments = dao.getTreatments();
        
        assertEquals("Size differs from 1", 1, treatments.size());
        
        assertEquals("Name differs", "TestTreatment", treatments.get(0).getName());
        
        Treatment retrievedTreatment = treatments.get(0);
        
        assertEquals("1st SubTreatmentDiffers", t1.getSubTreatments().get(0), retrievedTreatment.getSubTreatments().get(0));
    }
    
    @Test
    public void removeTreatmentTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        dao.storeTreatment(t1);
        dao.removeTreatment(t1);
        
        assertEquals("List of treatments not empty", 0, dao.getTreatments().size());
    }
    
    @Test
    public void storeProductTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        dao.storeTreatment(t1);
        Product p1 = new Product("Product1", "ProductDescription", t1);
        dao.storeProduct(p1);
        List<Product> products = dao.getProducts();
        
        assertEquals("Size is not 1", 1, products.size());
        
        Product retrievedProduct = dao.getProducts().get(0);
        
        assertEquals("Products differs", p1, retrievedProduct);
        
        assertEquals("Treatment differs", t1, retrievedProduct.getTreatment());
    }
    
    @Test
    public void removeProductTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        Product p1 = new Product("Product1", "ProductDescription", t1);
        dao.storeProduct(p1);
        dao.removeProduct(p1);
        
        assertEquals("Product not deleted", 0, dao.getProducts().size());
    }
    
    @Test
    public void storeSubProductTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        Product p1 = new Product("Product1", "ProductDescription", t1);
        dao.storeProduct(p1);
        SubProduct sp = new SubProduct("sp1", dao.getProducts().get(0), System.currentTimeMillis(), new Position("2A"));
        dao.storeSubProduct(sp);
        
        assertEquals("List of subproducts differs from 1", 1, dao.getProducts().size());
        
        assertEquals("Subproducts differ", sp, dao.getSubProducts().get(0));
        
        assertEquals("Product differs", p1.getName(), dao.getProducts().get(0).getName());
    }
}
/**
 * @author Lars Nielsen, Mailk Lasse Lund, Martin RÂ¿n Bundgaard
 * @Class PositionTest
 * @programmer Lars Nielsen
 */
package carletti.test.model;

import org.junit.After;
import org.junit.Test;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.Treatment;

public class PositionTest {

    @Test
    public void testPosition() {
        //fail("Not yet implemented");
        Position p = new Position("1A");
        
    }

    @Test
    public void testGetPosID() {
        //fail("Not yet implemented");
        Position p = new Position("1A");
        p.getPosID();
    }

    @Test
    public void testSetPosID() {
//      fail("Not yet implemented");
        Position p = new Position("1A");
        p.setPosID("2A");
    }

    @Test
    public void testPutSubProductOnPosition() {
//      fail("Not yet implemented");
        Treatment tm = new Treatment("Kain");
        Product pd = new Product("name", "description", tm);
        SubProduct sp = new SubProduct("Judas", pd, 10000, new Position("1M"));
        Position p = new Position("1A");
        p.putSubProductOnPosition(sp);
    }

    @Test
    public void testRemoveSubProductFromPosition() {
//      fail("Not yet implemented");
        Treatment tm = new Treatment("Kain");
        Product pd = new Product("name", "description", tm);
        SubProduct sp = new SubProduct("Judas", pd, 10000, new Position("2M"));
        Position p = new Position("1A");
        p.putSubProductOnPosition(sp);
        p.removeSubProductFromPosition();
    }
}
/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Product;
import carletti.model.Treatment;

public class ProductTest
{

    Treatment treatment1;
    Treatment treatment2;
    Product p1;
    
    @Before
    public void setUp() throws Exception
    {
        treatment1 = new Treatment("Treatment1"); 
        
        p1 = new Product("Produkt1", "Hej dette er en test",treatment1);
    }
    
    @Test
    public void Product()
    {
        Product p2 = new Product("produkt2", "test", treatment1);
        assertEquals(02, p2.getId());
        assertEquals("produkt2", p2.getName());
        assertEquals("test", p2.getDescription());
        assertEquals(treatment1, p2.getTreatment());
    }

    @Test
    public void testGetTreatment()
    {
        assertEquals(treatment1, p1.getTreatment());
    }

    @Test
    public void testSetTreatment()
    {
        
        p1.setTreatment(treatment2);
        assertEquals(treatment2, p1.getTreatment());
    }

    @Test
    public void testGetId()
    {
        assertEquals(01, p1.getId());
    }

    @Test
    public void testGetName()
    {
        assertEquals("Produkt1", p1.getName());
    }

    @Test
    public void testSetName()
    {
        p1.setName("Produkt11");
        assertEquals("Produkt11", p1.getName());
    }

    @Test
    public void testGetDescription()
    {
        assertEquals("Hej dette er en test", p1.getDescription());
    }

    @Test
    public void testSetDescription()
    {
        p1.setDescription("Den nye");
        assertEquals("Den nye", p1.getDescription());
    }

}
/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.dao.LocalDao;
import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;
import carletti.model.State;

public class ServiceTest
{

    private Product product;
    private Treatment treatment;
    private SubProduct subproduct;
    private SubProduct subproduct2;
//  private Dao localDao = LocalDao.getInstance();
    private Dao dao = JpaDao.getInstance();
    
    private Position p1;
    private Position p2;
    
    private Service service;
    
    
    @Before
    public void setUp() throws Exception
    {
        service = Service.getInstance(dao);
        treatment = service.createTreatment("testTreatment");
        treatment.createSubTreatment("subTreatment1", 100, 200, 300);
        treatment.createSubTreatment("subTreatment2", 400, 500, 600);
        
        product = service.createProduct("product", "dette er en test", treatment );
        p1 = new Position("1");
        p2 = new Position("2");
        subproduct = service.createSubProduct("Sub", product, p1);
        subproduct2 = service.createSubProduct("Sub2", product, p2);
    }
    
    @After
    public void setAfter()
    {   
        for (SubProduct sp : dao.getSubProducts())
        {
            service.removeSubproduct(sp);
        }
        
        for (Product p : service.getProducts())
        {
            service.removeProduct(p);
        }
            
                
        for (Treatment t : dao.getTreatments())
        {
            service.removeTreatment(t);
        }
        
        

    }

    @Test
    public void testCreateSubProduct()
    {
        SubProduct subProduct = service.createSubProduct("test", product, p1);
        assertEquals("test", service.getSubproduct(subProduct).getName());
        assertEquals(product, service.getSubproduct(subProduct).getProduct());
        assertEquals(p1, service.getSubproduct(subProduct).getPosition());
    }

    @Test
    public void testRemoveSubproduct()
    {
        service.removeSubproduct(subproduct);
        
        assertEquals(null, service.getSubproduct(subproduct));
    }

    @Test
    public void testDiscardSubProduct()
    {
        service.discardSubProduct(subproduct);
        assertEquals(State.WASTE , service.getSubproduct(subproduct).getState());
    }

    @Test
    public void testGetSubproduct()
    {
        assertEquals("Sub2", service.getSubproduct(subproduct2).getName());
        assertEquals(product, service.getSubproduct(subproduct2).getProduct());
    }

    @Test
    public void testCreateTreatment()
    {
        Treatment treatment = service.createTreatment("treatment");
        Product p = service.createProduct("name", "description", treatment);
        
        assertEquals(treatment, service.getProduct(p).getTreatment());
    }

    @Test
    public void testCreateProduct()
    {
        Product product = service.createProduct("name", "description", treatment);
        assertEquals(product, service.getProduct(product));
    }

    @Test
    public void testRemoveProduct()
    {
        Treatment t = service.createTreatment("sadf"); 
        Product product = service.createProduct("asdfdsa", "dsafdsaf", t);
        service.removeProduct(product);
        assertEquals(null, service.getProduct(product));
    }

    @Test
    public void testGetProduct()
    {
        assertEquals(product, service.getProduct(product));
    }

    @Test
    public void testNextTreatnemt()
    {
        assertEquals("subTreatment1", service.getSubproduct(subproduct).getCurrentSubTreatment().getName());
        service.nextTreatnemt(subproduct);
        assertEquals("subTreatment2", service.getSubproduct(subproduct).getCurrentSubTreatment().getName());
    }

    @Test
    public void testSubProductDone()
    {
        service.changeState(subproduct, State.DONE);
        assertEquals(State.DONE, service.getSubproduct(subproduct).getState());
    }

    @Test
    public void testGetProducts()
    {
        assertEquals(1, service.getProducts().size());  
    }

    @Test
    public void testGetSubProducts()
    {
        subproduct.setState(State.DONE);
        subproduct2.setState(State.WASTE);
        assertEquals(1, service.getSubProducts(State.DONE).size());
        assertEquals(1, service.getSubProducts(State.WASTE).size());
    }
    
}
package carletti.test.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;
import carletti.dao.JpaDao;

/**
 * 
 * @author Malik Lund
 *
 */
public class SubProductTest {
    Service service = Service.getInstance(JpaDao.getInstance());
    SubProduct subproduct;
    Treatment treatment;
    Product product;
    Position position;
    long timeAdded;

    @Before
    public void setUp() throws Exception {
        treatment = service.createTreatment("Treatment");
        treatment.createSubTreatment("Subtreatment 1", 10, 20, 30);
        treatment.createSubTreatment("Subtreatment 2", 20, 30, 40);
        treatment.createSubTreatment("Subtreatment 3", 30, 40, 50);
        product = service.createProduct("TestProduct", "Product to test", treatment);
        timeAdded = System.currentTimeMillis();
        position = new Position("A2");
        subproduct = new SubProduct("Name", product, timeAdded, position);
    }

    /**
     * Test getters.
     */
    @Test
    public void testSubProduct() {
        assertEquals("Time differs", timeAdded, subproduct.getTimeAdded());
        assertEquals("Name differs", "Name", subproduct.getName());
        assertEquals("State differs", State.DRYING, subproduct.getState());
        
        List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
        List<SubTreatment> expectedSubTreatments = treatment.getSubTreatments();
        assertEquals("Differing number of subtreatments", expectedSubTreatments.size(), actualSubTreatments.size());
        for (int i = 0; i < expectedSubTreatments.size(); i++){
            assertEquals("Differing SubTreatments", expectedSubTreatments.get(i), actualSubTreatments.get(i));
        }
        assertEquals("Current subtreatment differs", 0, subproduct.getCurrentSubTreatmentIndex());
        assertEquals("Product differs", product, subproduct.getProduct());
        assertEquals("Different subtreatments", treatment.getSubTreatments().get(0), subproduct.getCurrentSubTreatment());
    }
    
    /**
     * Test setId
     */
    @Test
    public void testSetId(){
        subproduct.setId(1337);
        assertEquals("Id not changed", 1337, subproduct.getId());
    }
    
    /**
     * Test setName
     */
    @Test
    public void testSetName(){
        subproduct.setName("NewName");
        assertEquals("Name not changed", "NewName", subproduct.getName());
    }
    
    /**
     * Test setState
     */
    @Test
    public void testSetState(){
        subproduct.setState(State.WASTE);
        assertEquals("State not changed", State.WASTE, subproduct.getState());
    }
    
    /**
     * Test setSubtreatments
     */
//  @Test
//  public void testSetSubtreatments(){
//      subproduct.nextSubTreatment();
//      subproduct.nextSubTreatment();
//      
//      Treatment newTreatment = service.createTreatment("NewTreatment");
//      newTreatment.createSubTreatment("NewSubTreatment", 50, 60, 70);
//      subproduct.setSubtreatments(newTreatment.getSubTreatments());
//      List<SubTreatment> expectedSubTreatments = newTreatment.getSubTreatments();
//      List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
//      for (int i = 0; i < expectedSubTreatments.size(); i++){
//          assertEquals("Subproducts differs", expectedSubTreatments.get(i), actualSubTreatments.get(i));
//      }
//      
//      subproduct.getCurrentSubTreatment();
//  }
    
//  /**
//   * Test addition of subTreatment
//   */
//  @Test
//  public void testAddSubTreatment(){
//      SubTreatment st = new SubTreatment("AddedTreatment", 10, 12, 14);
//      subproduct.addSubTreatment(st);
//      assertEquals("List not enlarged", 4, subproduct.getSubtreatments().size());
//      assertEquals("Last element not the recently added", st, subproduct.getSubtreatments().get(3));
//  }
    
    /**
     * Test product type change
     */
//  @Test
//  public void testSetProduct(){
//      subproduct.nextSubTreatment();
//      subproduct.nextSubTreatment();
//      subproduct.nextSubTreatment();
//      
//      Treatment newTreatment = service.createTreatment("NewTreatment");
//      newTreatment.createSubTreatment("T1", 1, 2, 3);
//      newTreatment.createSubTreatment("T2", 2, 3, 4);
//      Product newProduct = service.createProduct("NewProduct", "Description of new product", newTreatment);
//      subproduct.setProduct(newProduct);
//      assertEquals("Product differs", newProduct, subproduct.getProduct());
//
//      List<SubTreatment> expectedSubTreatments = newTreatment.getSubTreatments();
//      List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
//      for (int i = 0; i < expectedSubTreatments.size(); i++){
//          assertEquals("Subproducts differs", expectedSubTreatments.get(i), actualSubTreatments.get(i));
//      }
//      
//      subproduct.getCurrentSubTreatment();
//  }
    
    /**
     * Test nextSubTreatment()
     */
    @Test
    public void testNextSubTreatment(){
        subproduct.nextSubTreatment();
        assertEquals("Iteration error", treatment.getSubTreatments().get(1), subproduct.getCurrentSubTreatment());
        
        subproduct.nextSubTreatment();
        subproduct.nextSubTreatment();
        subproduct.nextSubTreatment();
        assertEquals("Iterated too far", treatment.getSubTreatments().get(2), subproduct.getCurrentSubTreatment());
    }
}
/**
 * @author Martin
 */


package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.SubTreatment;
import carletti.model.Treatment;

public class SubTreatmentTest
{
    private Treatment t;
    private SubTreatment st;

    @Before
    public void setUp() throws Exception
    {
        t = new Treatment("Treatment");
        t.createSubTreatment("subtreatment1", 10, 20, 30);
        st = t.getSubTreatments().get(0);
    }

    @Test
    public void testSubTreatment()
    {
        t = new Treatment("Treatment");
        t.createSubTreatment("Proeve", 40, 50, 60);
        SubTreatment s = t.getSubTreatments().get(0);
        assertEquals("Proeve", s.getName());
        assertEquals(40, s.getDryMin());
        assertEquals(50, s.getDryPrime());
        assertEquals(60, s.getDryMax());
    }

    @Test
    public void testGetDryMin()
    {
        assertEquals(10, st.getDryMin());
        
    }

    @Test
    public void testSetDryMin()
    {
        st.setDryMin(15);
        assertEquals(15, st.getDryMin());
    }

    @Test
    public void testGetDryPrime()
    {
        assertEquals(20, st.getDryPrime());
    }

    @Test
    public void testSetDryPrime()
    {
        st.setDryPrime(25);
        assertEquals(25, st.getDryPrime());
    }

    @Test
    public void testGetDryMax()
    {
        assertEquals(30, st.getDryMax());
    }

    @Test
    public void testSetDryMax()
    {
        st.setDryMax(65);
        assertEquals(65, st.getDryMax());
    }

    @Test
    public void testGetName()
    {
        assertEquals("subtreatment1", st.getName());
    }

    @Test
    public void testSetName()
    {
        st.setName("newName");
        assertEquals("newName", st.getName());
    }

    @Test
    public void testToString()
    {
        String s = "\n" + "subtreatment1" + "\n" + "min= " + "10" + "\nprime= " + "20" + "\nmax= " + "30";
        assertEquals(s, st.toString());
    }
}
/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Treatment;

public class TreatmentTest
{
    
    private Treatment t1;           

    @Before
    public void setUp() throws Exception
    {
        t1 = new Treatment("treatment1");
    }

    @Test
    public void testTreatment()
    {
        Treatment t2 = new Treatment("treatment2");
        assertEquals("treatment2", t2.getName());
    }

    @Test
    public void testCreateSubTreatment()
    {
        
        t1.createSubTreatment("name", 10, 20, 30);
        assertEquals("name", t1.getSubTreatments().get(0).getName());
        assertEquals(10, t1.getSubTreatments().get(0).getDryMin());
        assertEquals(20, t1.getSubTreatments().get(0).getDryPrime());
        assertEquals(30, t1.getSubTreatments().get(0).getDryMax());
    }

    @Test
    public void testGetName()
    {
        assertEquals("treatment1", t1.getName());
    }

    @Test
    public void testGetSubTreatments()
    {
        t1.createSubTreatment("name", 10, 20, 30);
        assertEquals("name", t1.getSubTreatments().get(0).getName());
        assertEquals(10, t1.getSubTreatments().get(0).getDryMin());
        assertEquals(20, t1.getSubTreatments().get(0).getDryPrime());
        assertEquals(30, t1.getSubTreatments().get(0).getDryMax());
    }

    @Test
    public void testSetName()
    {
        t1.setName("newName");
        assertEquals("newName", t1.getName());
    }

}
package carletti;

public class App {
    public static void main(String[] args){
        LoadGui lg = new LoadGui();
    }
}
/** @author Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 * 
 */
package carletti;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.dao.LocalDao;
import carletti.gui.MainFrame;
import carletti.service.Service;

@SuppressWarnings("serial")
public class LoadGui extends JFrame {

    private Dimension frameSize = new Dimension(300, 200);
    private JPanel selectPanel;

    // private int x = 20, y = 80;

    public LoadGui() {

        this.setTitle("Carletti - Load Screen");
        this.setSize(frameSize);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        selectPanel = new SelectorPanel();
        this.add(selectPanel);

        this.setVisible(true);

    }

    private class SelectorPanel extends JPanel implements ActionListener {
        private JButton btnOk;
        private JRadioButton rbtnLocal, rbtnDao;

        public SelectorPanel() {
            super();
            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            rbtnLocal = new JRadioButton("Local");
            rbtnDao = new JRadioButton("Database");
            btnOk = new JButton("Ok");

            rbtnLocal.addActionListener(this);
            rbtnDao.addActionListener(this);
            btnOk.addActionListener(this);

            layout.setHorizontalGroup(layout
                    .createSequentialGroup()
                    .addGroup(
                            layout.createParallelGroup(
                                    GroupLayout.Alignment.CENTER)
                                    .addComponent(rbtnLocal)
                                    .addComponent(btnOk)).addComponent(rbtnDao));

            layout.setVerticalGroup(layout
                    .createSequentialGroup()
                    .addGroup(
                            layout.createParallelGroup(
                                    GroupLayout.Alignment.CENTER)
                                    .addComponent(rbtnLocal)
                                    .addComponent(rbtnDao))
                    .addComponent(btnOk, 20, 20, 20));
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            Dao dao;
            if (ae.getSource().equals(btnOk)) {
                if (rbtnLocal.isSelected() == true
                        && rbtnDao.isSelected() == false) {
                    dao = LocalDao.getInstance();
                    startProgram(dao);
                } else if (rbtnLocal.isSelected() == false
                        && rbtnDao.isSelected() == true) {
                    dao = JpaDao.getInstance();
                    startProgram(dao);
                } 
            }
            else if(ae.getSource().equals(rbtnLocal)){
                rbtnDao.setSelected(false);
            }
            else if(ae.getSource().equals(rbtnDao)){
                rbtnLocal.setSelected(false);
            }
        }
    }
    
    private void startProgram(Dao dao){
        Service s = Service.getInstance(dao);
        s.createSomeObjects();
        this.setVisible(false);
        MainFrame mf = new MainFrame();
    }

}

---- /Users/tools/Desktop/Projekt/Kildekode/Samlet Kildekode.java

package carletti.dao.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RecreateDatabase
{
    public static void main(String[] args) {
        try {
            //connection to MySQL
        	Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://10.37.129.3/master", "sa",
					"lnc20020"); // "jdbc:jtds:sqlserver://10.37.129.3:1433/Cars"
			
            
            Statement stmt = myConnection.createStatement();
            stmt.executeUpdate("DROP DATABASE Carletti");
            stmt.executeUpdate("CREATE DATABASE Carletti");
            
            System.out.println("Database recreated!");
        }
        catch (Exception e) {
            System.out.println("Error connecting to database:  "
                    + e.getMessage());
        }
    }
}
package carletti.dao;

import java.util.List;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * @author Malik Lund
 *
 */
public interface Dao {

    public List<SubProduct> getSubProducts(State state);
    public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state);
    public void storeTreatment(Treatment t);
    public void removeTreatment(Treatment t);
    public void storeProduct(Product p);
    public void removeProduct(Product p);
    public void storeSubProduct(SubProduct sp);
    public void removeSubProduct(SubProduct sp);
    public void storePosition(Position p);
    public void removePosition(Position p);
    public List<Treatment> getTreatments();
    public List<Product> getProducts();
    public List<SubProduct> getSubProducts();
    public List<Position> getPositions();
}
/**
 * @author Lars Nielse, Malik L. Lund, Matrin R. Bungaard
 * @class JpaDao
 * @programmer Lars Nielsen
 */
package carletti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

public class JpaDao implements Dao{

    private static JpaDao uniqueInstance;
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    private JpaDao(){
        emf = Persistence.createEntityManagerFactory("Carletti");
        em = emf.createEntityManager();
    }
    
    /**
     * insures that we are only able to create on JpaDao instance
     * and it is done by implementing the Singelton design pattern
     * @return
     */
    public static JpaDao getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new JpaDao();
        }
        
        return uniqueInstance;
    }
    
    @Override
    /**
     * @author Malik Lund
     */
    public void storeSubProduct(SubProduct sp){
        em.getTransaction().begin();
        em.persist(sp);
        em.getTransaction().commit();
    }
    
    public void close(){
        em.close();
        emf.close();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<SubProduct> getSubProducts(State state) {
        Query query = em.createQuery("SELECT sp FROM SubProduct sp WHERE sp.state = :state");
        query.setParameter("state", state);
        return query.getResultList();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state) {
        em.getTransaction().begin();
        subProduct.setState(state);
        em.getTransaction().commit();
        return subProduct;
    }
    @Override
    /**
     * @author Malik Lund
     */
    public void storeProduct(Product p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void removeProduct(Product p) {
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void removeSubProduct(SubProduct sp) {
        em.getTransaction().begin();
        em.remove(sp);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<Product> getProducts() {
        Query query = em.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<SubProduct> getSubProducts() {
        Query query = em.createQuery("SELECT sp FROM SubProduct sp", SubProduct.class);
        return query.getResultList();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void storeTreatment(Treatment t) {
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void removeTreatment(Treatment t) {
        em.getTransaction().begin();
        em.remove(t);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<Treatment> getTreatments() {
        Query query = em.createQuery("SELECT t FROM Treatment t", Treatment.class);
        return query.getResultList();
    }
    
    //-----Lars
    @Override
    public List<Position> getPositions(){
        Query query = em.createQuery("SELECT p FROM Position p", Position.class);
        return query.getResultList();
    }

    @Override
    public void storePosition(Position p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        
        
    }

    @Override
    public void removePosition(Position p) {
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        
    }
    //-----
}
package carletti.dao;

import java.util.ArrayList;
import java.util.List;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * This class maintains connection, and any operations, with the database.
 * 
 * It is implemented as a Singleton-object to ensure there is only one
 * object interacting with the database.
 * To get an instance call the static method Dao.getInstance().
 * 
 * TODO Change implementation to use a database rather than lists.
 * 
 * @author Malik Lund
 *
 */
public class LocalDao implements Dao {
    private static LocalDao dao = null;
    private ArrayList<Treatment> treatments;
    private ArrayList<Product> products;
    private ArrayList<SubProduct> subproducts;
    private ArrayList<Position> positions;
    private int subProductnextID = 1;
    private int productNextID = 1;
    /**
     * Simply creates a Dao-object and initializes all
     * lists.
     */
    private LocalDao(){
        treatments = new ArrayList<Treatment>();
        products = new ArrayList<Product>();
        subproducts = new ArrayList<SubProduct>();      
        positions = new ArrayList<Position>();
    }
    
    /**
     * Creates a new instance of the Dao class on the first call
     * and merely returns this for any subsequent calls. 
     * @return An instance of Dao.
     */
    public static Dao getInstance(){
        if (dao == null){
            dao = new LocalDao();
        }
        return dao;
    }
    
    /**
     * Returns a list of SubProducts in the given state.
     * @param state The state of the returned SubProducts. 
     * @return A list of SubProducts in a given state. If state is null
     *         all SubProduct objects are returned.
     */
    public List<SubProduct> getSubProducts(State state){
        ArrayList<SubProduct> selectedSubproducts = new ArrayList<SubProduct>();
        for (SubProduct sp: subproducts){
            if (sp.getState().equals(state) || state == null){
                selectedSubproducts.add(sp);
            }
        }
        return selectedSubproducts;
    }
    
    /**
     * Changes the state of a SubProduct-object and saves it in the
     * database.
     * @param subProduct The object to change the state of.
     * @param state The state to change to.
     * @return The object with its new state.
     */
    public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state){
        subProduct.setState(state);
        return subProduct;
    }
    
    /**
     * Store a Product object in the database.
     * @param p The Product object to store.
     */
    public void storeProduct(Product p){
        Treatment t = p.getTreatment();
        if (!treatments.contains(t)){
            storeTreatment(t);
        }
        products.add(p);
    }
    
    /**
     * Delete a product type from the system.
     * @param p The Product object to delete.
     */
    public void removeProduct(Product p){
        products.remove(p);
    }

    /**
     * Store a SubProduct object in the database.
     * @param sp The SubProduct object to store.
     */
    public void storeSubProduct(SubProduct sp){
        subproducts.add(sp);
        
    }
    
    /**
     * Delete a sub-product from the system.
     * @param sp The SubProduct object to delete.
     */
    public void removeSubProduct(SubProduct sp){
        subproducts.remove(sp);
    }
    
    /**
     * Get a list of all product types.
     * @return A list of Product objects.
     */
    public List<Product> getProducts(){
        return new ArrayList<Product>(products);
    }
    
    /**
     * Get a list of all sub-product types.
     * @return A list of SubProduct objects.
     */
    public List<SubProduct> getSubProducts(){
        return new ArrayList<SubProduct>(subproducts);
    }
    
    public int getSubProducNextID(){
        return subProductnextID;
    }
    
    public void countSubProducID(){
        subProductnextID++;
    }
    
    public int getProducNextID(){
        return productNextID;
    }
    
    public void countProducID(){
        productNextID++;
    }

    @Override
    public void storeTreatment(Treatment t) {
        treatments.add(t);
    }

    @Override
    public void removeTreatment(Treatment t) {
        treatments.remove(t);
    }

    @Override
    public List<Treatment> getTreatments() {
        return new ArrayList<Treatment>(treatments);
    }
    
    //-----Lars
    @Override
    public List<Position> getPositions() {
        return new ArrayList<Position>(positions);
    }

    
    @Override
    public void storePosition(Position p) {
        positions.add(p);
        
    }

    @Override
    public void removePosition(Position p) {
        positions.remove(p);
        
    }
    //-----
    
    
}
package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;

/**
 * The CreateNewProductDialog class handles the creation
 * of a new product and it's associated treatment plan.
 * @author Malik Lund
 *
 */
public class CreateNewProductDialog extends JDialog {
    private Service service;
    private Controller controller;
    
    private boolean newTreatment;
    
    private JPanel mainPanel, productAndSubproductPanel, productInfoPanel,
                   productButtonsPanel, treatmentNamePanel, subTreatmentPanel, subTreatmentButtonsPanel;
    private JLabel lblNewProduct, lblName, lblDescription, lblTreatmentName, lblSubTreatment;
    private JTextField txfName, txfTreatmentName;
    private JTextArea txtAreaDescription;
    private JButton btnCreate, btnCancel, btnAddSubTreatment, btnSelectTreatment;
    private JTable subTreatmentsTable;
    private NewProductSubTreatmentsTableModel subTreatmentsTableModel;
    private JScrollPane productDescriptionScrollPane, subTreatmentsScrollPane;

    /**
     * 
     */
    public CreateNewProductDialog(){
        service = service.getInstance();
        this.setTitle("Create new product");
        this.setModal(true);

        controller = new Controller();
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);
        
        productAndSubproductPanel = new JPanel();
        GroupLayout outerGroupLayout = new GroupLayout(productAndSubproductPanel);
        productAndSubproductPanel.setLayout(outerGroupLayout);
        
        outerGroupLayout.setAutoCreateGaps(true);
        outerGroupLayout.setAutoCreateContainerGaps(true);
        
        productInfoPanel = new JPanel();
        productButtonsPanel = new JPanel();
        subTreatmentPanel = new JPanel();
        subTreatmentButtonsPanel = new JPanel();
        lblNewProduct = new JLabel("New Product");
        
        mainPanel.add(lblNewProduct, BorderLayout.NORTH);
        
        //------------
        // General grouping of product information and subtreatment information
        
        outerGroupLayout.setHorizontalGroup(
            outerGroupLayout.createSequentialGroup()
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(productInfoPanel)
                    .addComponent(productButtonsPanel))
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(subTreatmentPanel)
                    .addComponent(subTreatmentButtonsPanel))
        );
        
        outerGroupLayout.setVerticalGroup(
            outerGroupLayout.createSequentialGroup()
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(productInfoPanel)
                    .addComponent(subTreatmentPanel))
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(productButtonsPanel)
                    .addComponent(subTreatmentButtonsPanel))
        );
        
        mainPanel.add(productAndSubproductPanel);
        
        //------------
        // Product name and description
        
        GroupLayout productGroupLayout = new GroupLayout(productInfoPanel);
        productInfoPanel.setLayout(productGroupLayout);
        
        productGroupLayout.setAutoCreateGaps(true);
        productGroupLayout.setAutoCreateContainerGaps(true);
        
        lblName = new JLabel("Name:");
        lblDescription = new JLabel("Description:");
        txfName = new JTextField();
        txtAreaDescription = new JTextArea();
        txtAreaDescription.setLineWrap(true);
        productDescriptionScrollPane = new JScrollPane(txtAreaDescription);
        
        productGroupLayout.setHorizontalGroup(
            productGroupLayout.createSequentialGroup()
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(lblDescription))
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txfName, 150, 175, 200)
                    .addComponent(productDescriptionScrollPane, 250, 250, Short.MAX_VALUE))
        );
        
        productGroupLayout.setVerticalGroup(
            productGroupLayout.createSequentialGroup()
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txfName))
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescription)
                    .addComponent(productDescriptionScrollPane, 100, 100, Short.MAX_VALUE))
        );
        
        //--------------
        // Product create and cancel buttons
        
        GroupLayout productButtonsGroupLayout = new GroupLayout(productButtonsPanel);
        productButtonsPanel.setLayout(productButtonsGroupLayout);
        
        productButtonsGroupLayout.setAutoCreateGaps(true);
        productButtonsGroupLayout.setAutoCreateContainerGaps(true);
        
        btnCreate = new JButton("Create");
        btnCancel = new JButton("Cancel");
        
        btnCreate.addActionListener(controller);
        btnCancel.addActionListener(controller);
        
        productButtonsGroupLayout.setHorizontalGroup(
            productButtonsGroupLayout.createSequentialGroup()
                .addComponent(btnCreate)
                .addComponent(btnCancel)
        );
        
        productButtonsGroupLayout.setVerticalGroup(
            productButtonsGroupLayout.createSequentialGroup()
                .addGroup(productButtonsGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnCancel))
        );
        
        //-------------
        // Subtreatments table
        
        GroupLayout subTreatmentsGroupLayout = new GroupLayout(subTreatmentPanel);
        subTreatmentPanel.setLayout(subTreatmentsGroupLayout);
        
        subTreatmentsGroupLayout.setAutoCreateGaps(true);
        subTreatmentsGroupLayout.setAutoCreateContainerGaps(true);
        
        lblSubTreatment = new JLabel("Treatments");
        treatmentNamePanel = new JPanel();
        lblTreatmentName = new JLabel("Name:    ");
        txfTreatmentName = new JTextField();
        subTreatmentsTableModel = new NewProductSubTreatmentsTableModel();
        subTreatmentsTable = new JTable(subTreatmentsTableModel);
        subTreatmentsScrollPane = new JScrollPane(subTreatmentsTable);
        
        treatmentNamePanel.setLayout(new BoxLayout(treatmentNamePanel, BoxLayout.X_AXIS));
        treatmentNamePanel.add(lblTreatmentName);
        treatmentNamePanel.add(txfTreatmentName);
        txfTreatmentName.setEnabled(false);
        subTreatmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        subTreatmentsGroupLayout.setHorizontalGroup(
            subTreatmentsGroupLayout.createSequentialGroup()
                .addGroup(subTreatmentsGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblSubTreatment)
                    .addComponent(treatmentNamePanel)
                    .addComponent(subTreatmentsScrollPane, 400, 400, 400))
        );
        
        subTreatmentsGroupLayout.setVerticalGroup(
            subTreatmentsGroupLayout.createSequentialGroup()
                .addComponent(lblSubTreatment)
                .addComponent(treatmentNamePanel)
                .addComponent(subTreatmentsScrollPane, 100, 100, Short.MAX_VALUE)
        );
        
        //-----------
        // Treatment buttons.
        
        GroupLayout subTreatmentButtonsGroupLayout = new GroupLayout(subTreatmentButtonsPanel);
        subTreatmentButtonsPanel.setLayout(subTreatmentButtonsGroupLayout);
        
        subTreatmentButtonsGroupLayout.setAutoCreateGaps(true);
        subTreatmentButtonsGroupLayout.setAutoCreateContainerGaps(true);
        
        btnAddSubTreatment = new JButton("New Treatment");
        btnSelectTreatment = new JButton("Select existing treatment");
        
        btnAddSubTreatment.addActionListener(controller);
        btnSelectTreatment.addActionListener(controller);
        
        subTreatmentButtonsGroupLayout.setHorizontalGroup(
            subTreatmentButtonsGroupLayout
                .createSequentialGroup()
                    .addComponent(btnAddSubTreatment)
                    .addComponent(btnSelectTreatment)
        );
        
        subTreatmentButtonsGroupLayout.setVerticalGroup(
            subTreatmentButtonsGroupLayout
                .createParallelGroup()
                    .addComponent(btnAddSubTreatment)
                    .addComponent(btnSelectTreatment)
        );
        
        this.pack();
    }
    
    /**
     * Handles all input for this class.
     * @author Malik Lund
     *
     */
    private class Controller implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            /**
             * Create product button.
             */
            if (ae.getSource() == btnCreate){
                boolean success = true;
                
                String subProductName = txfName.getText();
                if (subProductName.length() <= 0){
                    JOptionPane.showMessageDialog(null, "Subproduct name is empty!");
                    success = false;
                }
                
                String treatmentName = txfTreatmentName.getText();
                if (success && treatmentName.length() <= 0){
                    JOptionPane.showMessageDialog(null, "Treatment name is empty!");
                    success = false;
                }
                
                List<SubTreatment> data = subTreatmentsTableModel.getData().getSubTreatments();
                if (success && data.size() <= 0){
                    JOptionPane.showMessageDialog(null, "Treatment has no subtreatments!");
                    success = false;
                }
                
                if (success){
                    Treatment treatment = service.createTreatment(txfTreatmentName.getText());
                    for (int i = 0; i < data.size(); i++){
                        String name = data.get(i).getName();
                        long min = data.get(i).getDryMin();
                        long optimal = data.get(i).getDryPrime();
                        long max = data.get(i).getDryMax();
                        treatment.createSubTreatment(name, min, optimal, max);
                    }
                    service.createProduct(txfName.getText(), txtAreaDescription.getText(), treatment);
                    CreateNewProductDialog.this.setVisible(false);
                }
            }
            /**
             * Cancel creation of product button.
             */
            else if (ae.getSource() == btnCancel){
                CreateNewProductDialog.this.setVisible(false);
            }
            
            /**
             * New/Add subtreatment button.
             */
            else if (ae.getSource() == btnAddSubTreatment){
                if (newTreatment){
                    CreateNewSubTreatmentDialog createSubTreatmentDialog = new CreateNewSubTreatmentDialog(subTreatmentsTableModel);
                    createSubTreatmentDialog.setLocationRelativeTo(CreateNewProductDialog.this);
                    createSubTreatmentDialog.setVisible(true);
                } else {
                    newTreatment = true;
                    txfTreatmentName.setEnabled(true);
                    txfTreatmentName.setText("");
                    btnAddSubTreatment.setText("Add subtreatment");
                    subTreatmentsTableModel.setData(new Treatment("Temp"));
                }
            }
            /**
             * Select treatment button. 
             */
            else if (ae.getSource() == btnSelectTreatment){
                // save new Treatment in case you regret 
                // selecting an existing one
                Treatment temporaryTreatment = null;
                if (newTreatment){
                    temporaryTreatment = subTreatmentsTableModel.getData();
                    temporaryTreatment.setName(txfTreatmentName.getText());
                }
                
                // prepare table and tablemodel
                subTreatmentsTableModel.setData(new Treatment("Temp"));
                subTreatmentsTableModel.removeTableModelListener(subTreatmentsTable); // "disconnect" table 
                txfTreatmentName.setText("");
                
                // start selection
                SelectTreatmentDialog selectTreatmentDialog = new SelectTreatmentDialog(subTreatmentsTableModel);
                selectTreatmentDialog.setLocationRelativeTo(CreateNewProductDialog.this);
                selectTreatmentDialog.setVisible(true);
                
                // handle selection
                Treatment treatment = subTreatmentsTableModel.getData();
                if (!treatment.getName().equals("Temp")){
                    // selected an existing treatment
                    txfTreatmentName.setText(treatment.getName());
                    txfTreatmentName.setEnabled(false);
                    btnAddSubTreatment.setText("New subtreatment");
                    newTreatment = false;
                } else {
                    // didn't select an existing treatment
                    if (newTreatment){
                        // revert to previous data.
                        subTreatmentsTableModel.setData(temporaryTreatment);
                        txfTreatmentName.setText(temporaryTreatment.getName());
                    } else {
                        // no previsous data.
                        subTreatmentsTableModel.setData(new Treatment("Temp"));
                    }
                }
                
                subTreatmentsTableModel.addTableModelListener(subTreatmentsTable); // "reconnect" table
                subTreatmentsTableModel.fireTableDataChanged();

            }
        }
    }
}
package carletti.gui.dialogs;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Handles the creation of a new SubTreatment and
 * associates it with creation of a new Treatment.
 * @author Malik Lund
 *
 */
public class CreateNewSubTreatmentDialog extends JDialog {

    private Controller controller;
    private JPanel mainPanel, infoPanel;
    private TimeSelectorPanel minPanel, optimalPanel, maxPanel;
    private JLabel lblNew, lblName, lblMin, lblOptimal, lblMax;
    private JTextField txfName;
    private JButton btnCreate, btnCancel;

    private NewProductSubTreatmentsTableModel subTreatmentsTableModel;
    
    /**
     * 
     * @param subTreatmentsTableModel
     */
    public CreateNewSubTreatmentDialog(NewProductSubTreatmentsTableModel subTreatmentsTableModel){
        this.getContentPane().setLayout(new FlowLayout());
        this.setModal(true);
        this.setResizable(false);

        
        this.subTreatmentsTableModel = subTreatmentsTableModel;
        controller = new Controller();
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);
        
        infoPanel = new JPanel();
        GroupLayout layout = new GroupLayout(infoPanel);
        infoPanel.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        minPanel = new TimeSelectorPanel();
        optimalPanel = new TimeSelectorPanel();
        maxPanel = new TimeSelectorPanel();
        lblNew = new JLabel("New subtreatment");
        lblName = new JLabel("Name:");
        lblMin = new JLabel("Minimum:");
        lblOptimal = new JLabel("Optimal:");
        lblMax = new JLabel("Maximum:");
        txfName = new JTextField();
        btnCreate = new JButton("Create");
        btnCancel = new JButton("Cancel");
        
        btnCreate.addActionListener(controller);
        btnCancel.addActionListener(controller);
        
        mainPanel.add(lblNew, BorderLayout.NORTH);
        
        /**
         * Define main grouping horizontally.
         */
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(lblMin)
                    .addComponent(lblOptimal)
                    .addComponent(lblMax)
                    .addComponent(btnCreate))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txfName)
                    .addComponent(minPanel)
                    .addComponent(optimalPanel)
                    .addComponent(maxPanel)
                    .addComponent(btnCancel))
        );
        
        /**
         * Define main grouping vertically.
         */
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txfName))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMin)
                    .addComponent(minPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOptimal)
                    .addComponent(optimalPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMax)
                    .addComponent(maxPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnCancel))
        );

        mainPanel.add(infoPanel);
        
        pack();
    }
    
    /**
     * This private class handles all input events.
     * @author Malik
     *
     */
    private class Controller implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent ae) {
            /**
             * Create button.
             */
            if (ae.getSource() == btnCreate){
                // --- input verification start ---
                boolean error = false;
                String name = txfName.getText();
                if (name.length() <= 0){
                    JOptionPane.showMessageDialog(null, "Name is empty!");
                    error = true;
                }
                
                long min = minPanel.getTime();
                if (!error && min < 0){
                    JOptionPane.showMessageDialog(null, "Minimum must be larger than 0!");
                    error = true;
                }
                
                long optimal = optimalPanel.getTime();
                if (!error && optimal < min){
                    JOptionPane.showMessageDialog(null, "Optimal must be larger than minimum!");
                    error = true;
                }
                
                long max = maxPanel.getTime();
                if (!error && max < optimal){
                    JOptionPane.showMessageDialog(null, "Maximum must be larger than optimal!");
                    error = true;
                }
                // --- input verification end ---
                
                if (!error){
                    subTreatmentsTableModel.newSubTreatment(name, min, optimal, max);
                    CreateNewSubTreatmentDialog.this.setVisible(false);
                }
            }
            /**
             * Cancel button.
             */
            else if (ae.getSource() == btnCancel){
                CreateNewSubTreatmentDialog.this.setVisible(false);
            }
        }
    }
    
    /**
     * Creates a panel containing four SelectorPanel objects (see below). One for
     * selecting number of days, one for number of hours, one for minutes
     * and one for seconds (it is assumed that it is unnecessary to input
     * milliseconds as values).
     * @author Malik Lund
     *
     */
    class TimeSelectorPanel extends JPanel{
        
        private JLabel lblDays, lblHours, lblMinutes, lblSeconds;
        private SelectorPanel daySelector, hourSelector, minuteSelector, secondSelector;
        
        /**
         * 
         */
        public TimeSelectorPanel(){
            super();
            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            lblDays = new JLabel("Days");
            lblHours = new JLabel("Hours");
            lblMinutes = new JLabel("Minutes");
            lblSeconds = new JLabel("Seconds");
            daySelector = new SelectorPanel(100);
            hourSelector = new SelectorPanel(24);
            minuteSelector = new SelectorPanel(60);
            secondSelector = new SelectorPanel(60);
            
            layout.setHorizontalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblDays)
                        .addComponent(daySelector))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblHours)
                        .addComponent(hourSelector))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblMinutes)
                        .addComponent(minuteSelector))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblSeconds)
                        .addComponent(secondSelector))
            );
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDays)
                        .addComponent(lblHours)
                        .addComponent(lblMinutes)
                        .addComponent(lblSeconds))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(daySelector)
                        .addComponent(hourSelector)
                        .addComponent(minuteSelector)
                        .addComponent(secondSelector))
            );
        }
        
        /**
         * Returns the time represented by all four textfields
         * represented in milliseconds.
         * @return
         */
        public long getTime(){
            long result = daySelector.getTime() * 1000 * 60 * 60 * 24;
            result += hourSelector.getTime() * 1000 * 60 * 60;
            result += minuteSelector.getTime() * 1000 * 60;
            result += secondSelector.getTime() * 1000;
            return result;
        }
    }
    
    /**
     * A panel containing a plus- and minus-button and a textfield with
     * a number. Pressing the plus button increments the value in the 
     * textfield by one, while pressing the minus button decrements the
     * value by one.
     * If the value gets larger than the given crossover point the value
     * wraps around to zero. If a value gets smaller than 0 it wraps around
     * to the larges value below the crossover point.
     * @author Malik Lund
     *
     */
    private class SelectorPanel extends JPanel implements ActionListener {
        private int crossover;
        private JTextField txfNumber;
        private JButton btnPlus, btnMinus;
        
        /**
         * 
         * @param crossover Value where the number crosses over
         */
        public SelectorPanel(int crossover){
            super();
            this.crossover = crossover;
            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            txfNumber = new JTextField("0");
            btnPlus = new JButton("+");
            btnMinus = new JButton("-");
            
            txfNumber.setHorizontalAlignment(JTextField.RIGHT);
            
            btnPlus.addActionListener(this);
            btnMinus.addActionListener(this);
            
            layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(btnPlus)
                    .addComponent(txfNumber)
                    .addComponent(btnMinus)
            );
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(btnPlus, 20, 20, 20)
                    .addComponent(txfNumber)
                    .addComponent(btnMinus, 20, 20, 20)
            );
        }
        
        /**
         * 
         * @return The the number in the text field as a long.
         * @throws NumberFormatException
         */
        public long getTime() throws NumberFormatException{
            return Long.parseLong(txfNumber.getText());
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            /**
             * Increment button.
             */
            if (ae.getSource() == btnPlus){
                int number = getNumber();
                number = (number + 1) % crossover;
                txfNumber.setText(number + "");
            }
            
            /**
             * Decrement button.
             */
            else if (ae.getSource() == btnMinus){
                int number = getNumber();
                number = (number - 1);
                if (number < 0){
                    number = crossover - 1;
                }
                txfNumber.setText(number + "");
            }
        }
        
        /**
         * Performs various safety checks on the value in the
         * text field.
         * @return
         * @throws NumberFormatException
         */
        private int getNumber() throws NumberFormatException{
            int number = 0;
            if (txfNumber.getText().length() > 0){
                number = Integer.parseInt(txfNumber.getText());
            } else {
                number = 0;
            }
            
            if (number >= crossover){
                number = crossover - 1;
            }
            if (number < 0){
                number = 0;
            }
            return number;
        }
    }
}
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.LongToStringParser;
import carletti.model.SubTreatment;
import carletti.model.Treatment;

/**
 * This class is used by the JTable in CreateNewSubTreatmentDialog.
 * It contains the current list of SubTreatment-objects to be added to a
 * new Treatment-object in a new Product-object.
 * @author Malik Lund
 *
 */
public class NewProductSubTreatmentsTableModel extends AbstractTableModel {
    // Column headers.
    private String[] coloumnNames = {"Name", "Minimum", "Optimal", "Maximum"};
    // The actual data.
    private Treatment treatment = new Treatment("temp");

    @Override
    public int getColumnCount() {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount() {
        return treatment.getSubTreatments().size();
    }
    
    @Override
    public String getColumnName(int col){
        return coloumnNames[col];
    }

    @Override
    /**
     * @return col 1: Name, col 2: Min, col 3: optimal, col 4: max
     */
    public Object getValueAt(int row, int col) {
        List<SubTreatment> subTreatments = treatment.getSubTreatments();
        if (col < 1){
            return subTreatments.get(row).getName();
        } else {
            long time;
            if (col == 1){
                time = subTreatments.get(row).getDryMin();
            }
            else if (col == 2){
                time = subTreatments.get(row).getDryPrime();
            } else {
                time = subTreatments.get(row).getDryMax();
            }
            return LongToStringParser.parseLongToString(time);
        }
    }
    
    @Override
    /**
     * Simply returns the class-type of each column.
     */
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }

    /**
     * Add a new SubTreatment to the list of subTreatments.
     * @param name Name of the subtreatment.
     * @param min Minimum drying time.
     * @param optimal Optimal or prime drying time.
     * @param max Maximum drying time.
     */
    public void newSubTreatment(String name, long min, long optimal, long max) {
        treatment.createSubTreatment(name, min, optimal, max);
        fireTableDataChanged();
    }
    
    /**
     * Returns a temporary treatment containing the subtreatments
     * @return A Treatment object containing the SubTreatments. OBS! Recreate using
     *         the Service class!! This is only temporary and it will not be saved
     *         in the database!
     */
    public Treatment getData(){
        return treatment;
    }
    
    /**
     * Replace the data in the table with the
     * SubTreatments in the given Treatment.
     * @param subTreatments
     */
    public void setData(Treatment t){
        treatment = t;
        fireTableDataChanged();
    }

}
package carletti.gui.dialogs;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import carletti.model.Position;
import carletti.model.Product;
import carletti.service.Service;

/**
 *@group Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bundgaard
 *@class NewSubProductDialog
 *@programmer Lars Nielsen
 */
/*
 * This class is harcoded due to certain requriments, which couldn't 
 * be achive with JWindowsBuild
 */
public class NewSubProductDialog extends JDialog {
    
    private Service service; // Holds an singelton instance of the Service
    
    //GUI componenst
    private JTextField txfName;
    private JComboBox comboPos;
    private JLabel lblName, lblProd;
    private JScrollPane jspProducts;
    private JList prodList;
    private JButton btnSub, btnCan;
    
    //Start coordinates for the different GUI objects
    private int x = 20, y = 20;
    
    //Dimensions for the GUI components
    private Dimension defaultSize = new Dimension(360, 360);
    private Dimension btnSize = new Dimension(140, 20);
    private Dimension lblSize = new Dimension(140, 20);
    private Dimension txfSize = new Dimension(160, 20);
    private Dimension jspSize = new Dimension(320, 160);

    private Controller btnCtrl; //Holds an instance of the Controller 

    public NewSubProductDialog() {
        
        service = Service.getInstance(); //Service is instantiated 
        
        btnCtrl = new Controller(); //Controller is instantiated 

        //Generel preference  for the JDialog
        this.setSize(defaultSize);
        this.setResizable(false);
        this.setTitle("New Subproduct");
        this.setLayout(null);
        this.setModal(true);

        /*
         * Instansation of all GUI componetens and the preferences 
         */
        lblName = new JLabel();
        lblName.setText("Name");
        lblName.setSize(lblSize);
        lblName.setLocation(x, y);
        this.add(lblName);

        x += 160;

        txfName = new JTextField();
        txfName.setSize(txfSize);
        txfName.setLocation(x, y);
        this.add(txfName);
        
        y += 40;
        
        comboPos = new JComboBox(getPositionsAvailable().toArray());
        comboPos.setSize(txfSize);
        comboPos.setLocation(x, y);
        this.add(comboPos);

        x = 20;
        

        lblProd = new JLabel();
        lblProd.setText("Products");
        lblProd.setSize(lblSize);
        lblProd.setLocation(x, y);
        this.add(lblProd);

        y += 40;

        prodList = new JList();
        prodList.setListData(service.getProducts().toArray());
        prodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jspProducts = new JScrollPane(prodList);
        jspProducts.setSize(jspSize);
        jspProducts.setLocation(x, y);
        this.add(jspProducts);

        y += 180;

        btnSub = new JButton("Submit");
        btnSub.addActionListener(btnCtrl);
        btnSub.setSize(btnSize);
        btnSub.setLocation(x, y);
        this.add(btnSub);

        x += 180;

        btnCan = new JButton("Cancel");
        btnCan.addActionListener(btnCtrl);
        btnCan.setSize(btnSize);
        btnCan.setLocation(x, y);
        this.add(btnCan);

    }

    /**
     * Allows to set the visibility in the Controller class
     * @param visibility
     */
    private void setDialogVisibility(boolean visibility) {
        this.setVisible(visibility);
    }
    
    /**
     * Used to get all positions which is not occurpied by a SubProudct
     * @return
     */
    private List<Position> getPositionsAvailable(){
        
        ArrayList<Position> avaPos = new ArrayList<Position>();
        List<Position> allPos = service.getPositions();
        
        for(int i = 0; i < allPos.size(); i++){
            if(allPos.get(i).getSubProduct() == null){
                avaPos.add(allPos.get(i));
            }
        }
        return avaPos;
    }

    /**
     * The inner class Controller, is orgnaised so we can controll which button
     * events is actived
     */
    private class Controller implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource().equals(btnSub)) {
                String name = txfName.getText();
                Product p = (Product) prodList.getSelectedValue();
                Position pos = (Position) comboPos.getSelectedItem();
                
                service.createSubProduct(name, p, pos);
                NewSubProductDialog.this.setVisible(false);

            } else if (ae.getSource().equals(btnCan)) {
                setDialogVisibility(false);
            }
        }
    }   
}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import carletti.gui.*;
import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import carletti.gui.UpdaterThread;
import carletti.model.Position;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;
import carletti.dao.JpaDao;

public class NextSubTreatmentDialog extends JFrame
{
    private Service service;
    
    private Dimension minsize = new Dimension(600, 400);
    private JTable nextSubProcuctTable;
    private JButton btnNextSubTreatment;
    private JButton btnAnnuller;
    private NextSubTreatmentDialogTableModel tableModel;
    private JScrollPane nextSubProcuctScrollPane;
    private Controller btnCtrl;
    private NextSubTreatmentDialogTableModel nextSubTreatmentDialogTableModel;

    public NextSubTreatmentDialog()
    {
        service = Service.getInstance();
        btnCtrl = new Controller();
        this.setMinimumSize(minsize);

        tableModel = new NextSubTreatmentDialogTableModel();
        nextSubProcuctTable = new JTable(tableModel);

        nextSubProcuctScrollPane = new JScrollPane(nextSubProcuctTable);
        nextSubProcuctScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(nextSubProcuctScrollPane, BorderLayout.CENTER);

        btnNextSubTreatment = new JButton("Next Subtreatment");
        btnNextSubTreatment.addActionListener(btnCtrl);

        btnAnnuller = new JButton("Cancel");
        btnAnnuller.addActionListener(btnCtrl);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
        .createParallelGroup(Alignment.TRAILING).addComponent(nextSubProcuctScrollPane,
        GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE).addGroup(groupLayout
        .createSequentialGroup()
        .addComponent(btnNextSubTreatment)
        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(btnAnnuller)))
        .addGap(0)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
        .addComponent(nextSubProcuctScrollPane, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        .addPreferredGap(ComponentPlacement.RELATED)
        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        .addComponent(btnNextSubTreatment)
        .addComponent(btnAnnuller))
        .addGap(3)));
        
        getContentPane().setLayout(groupLayout);

        this.setVisible(true);
    }

    public void update()
    {

    }

    private class Controller implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {

            if (ae.getSource().equals(btnAnnuller))
            {
                NextSubTreatmentDialog.this.setVisible(false);
            }

            else if (ae.getSource().equals(btnNextSubTreatment))
            {
                int i = nextSubProcuctTable.getSelectedRow();
                if (i < 0)
                {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                } 
                else {
                    // --- Malik Lund start---
                    SubProduct sp = service.getSubProducts(State.TREATMENT).get(i);
                    
                    if (sp.getCurrentSubTreatmentIndex() < sp.getSubtreatments().size() - 1){
                        Position p = new Position("null");
                        PositionSelectionDialog selectionDialog = new PositionSelectionDialog(p);
                        selectionDialog.setVisible(true);
                        
                        if (!p.getPosID().equals("null")){
                            List<Position> positions = service.getPositions();
                            boolean found = false;
                            int index = 0;
                            Position wantedPosition = null;
                            while (!found && index < positions.size()){
                                wantedPosition = positions.get(i);
                                if (wantedPosition.getPosID().equals(p.getPosID())){
                                    found = true;
                                } else {
                                    i++;
                                }
                            }
                            
                            if (found){
                                service.nextTreatnemt(sp);
                                sp.setPosition(wantedPosition);
                                NextSubTreatmentDialog.this.setVisible(false);
                            }
                        }
                        
                    } else {
                        service.nextTreatnemt(sp);
                        NextSubTreatmentDialog.this.setVisible(false);
                    }
                    // --- Malik Lund end ---
                }
            }
        }
    }
    
    /**
     * 
     * @author Malik Lund
     */
    private class PositionSelectionDialog extends JDialog implements ActionListener{
        private Position position;
        
        private JPanel mainPanel, buttonsPanel;
        private JComboBox comboBox;
        private JButton btnOk, btnCancel;
        
        public PositionSelectionDialog(Position position){
            this.position = position;
            this.setLocationRelativeTo(NextSubTreatmentDialog.this);
            this.setModal(true);
            
            mainPanel = new JPanel();
            
            GroupLayout layout = new GroupLayout(this.getContentPane());
            this.getContentPane().setLayout(layout);
            
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            btnOk = new JButton("Ok");
            btnCancel = new JButton("Cancel");
            comboBox = new JComboBox(getPositionsAvailable().toArray());
            
            buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
            buttonsPanel.add(btnOk);
            buttonsPanel.add(btnCancel);
            
            btnOk.addActionListener(this);
            btnCancel.addActionListener(this);
            
            layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(comboBox)
                    .addComponent(buttonsPanel)
            );
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(comboBox)
                    .addComponent(buttonsPanel)
            );
            
            this.pack();
        }
        
        private List<Position> getPositionsAvailable(){
            
            ArrayList<Position> avaPos = new ArrayList<Position>();
            List<Position> allPos = service.getPositions();
            
            for(int i = 0; i < allPos.size(); i++){
                if(allPos.get(i).getSubProduct() == null){
                    avaPos.add(allPos.get(i));
                }
            }
            return avaPos;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == btnOk){
                position.setPosID(((Position)comboBox.getSelectedItem()).getPosID());
                this.setVisible(false);
            }
            else if (ae.getSource() == btnCancel){
                this.setVisible(false);
            }
        }
    }
}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;
import carletti.dao.JpaDao;

public class NextSubTreatmentDialogTableModel extends AbstractTableModel
{
    private Service service;
    // Column headers.
    private String[] coloumnNames =
    { "ID", "Name", "Subtreatment", "Number of Subtreatments" };
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State treatment = State.TREATMENT;

    public NextSubTreatmentDialogTableModel()
    {
        service = Service.getInstance();
        newData = service.getSubProducts(treatment);
    }

    @Override
    public int getColumnCount()
    {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return newData.size();
    }

    @Override
    public String getColumnName(int col)
    {
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        SubProduct sp = newData.get(row);
        Object[] value =
        {

        new Integer(sp.getId()), sp.getName(),
                sp.getCurrentSubTreatment().getName(),
                sp.getSubtreatments().size() };
        return value[col];
    }

    @Override
    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    public SubProduct selctedSubProduct(int selectedRow)
    {
        if ( selectedRow >= 0)
        {
            return newData.get(selectedRow);
        }
        else
            return null;
    }

    public void updateData()
    {
        newData = service.getSubProducts(treatment);
    }
        
}
package carletti.gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;

/**
 * Handles the selection of an existing treatment plan.
 * This treatment plan is then associated with a
 * product in creation.
 * @author Malik Lund
 */
public class SelectTreatmentDialog extends JDialog {
    
    private Service service;
    private NewProductSubTreatmentsTableModel tableModel;
    private Controller controller;
    private List<Treatment> treatments;
    
    private JPanel buttonsPanel;
    private JLabel lblTreatments, lblSubTreatments;
    private JList treatmentsList;
    private JScrollPane treatmentsListScrollPane, subTreatmentsTableScrollPane;
    private JTable subTreatmentsTable;
    private JButton btnSelect, btnCancel;
    
    public SelectTreatmentDialog(NewProductSubTreatmentsTableModel tableModel){
        this.setModal(true);
        this.setTitle("Select treatment");
        
        service = Service.getInstance();
        this.tableModel = tableModel;
        controller = new Controller();
        
        lblTreatments = new JLabel("Treatments:");
        treatments = service.getTreatments();
        String[] treatmentsArray = new String[treatments.size()];
        for (int i = 0; i < treatmentsArray.length; i++){
            treatmentsArray[i] = treatments.get(i).getName();
        }
        treatmentsList = new JList(treatmentsArray);
        treatmentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treatmentsList.addListSelectionListener(controller);
        treatmentsListScrollPane = new JScrollPane(treatmentsList);

        lblSubTreatments = new JLabel("Subtreatments");
        subTreatmentsTable = new JTable(tableModel);
        subTreatmentsTableScrollPane = new JScrollPane(subTreatmentsTable);

        buttonsPanel = new JPanel();
        btnSelect = new JButton("Select");
        btnSelect.addActionListener(controller);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(controller);
        buttonsPanel.add(btnSelect);
        buttonsPanel.add(btnCancel);
        
        
        // ----------------
        // Main layout
        
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblTreatments)
                    .addComponent(treatmentsListScrollPane)
                    .addComponent(buttonsPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblSubTreatments)
                    .addComponent(subTreatmentsTableScrollPane))
        );
            
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTreatments)
                    .addComponent(lblSubTreatments))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(treatmentsListScrollPane)
                    .addComponent(subTreatmentsTableScrollPane))
                .addComponent(buttonsPanel)
        );
        
        // ---------------------
        // buttons
        GroupLayout buttonsLayout = new GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsLayout);
        buttonsLayout.setAutoCreateGaps(true);
        buttonsLayout.setAutoCreateContainerGaps(true);
        
        buttonsLayout.setHorizontalGroup(
            buttonsLayout.createSequentialGroup()
                .addComponent(btnSelect)
                .addComponent(btnCancel)
        );
        
        buttonsLayout.setVerticalGroup(
            buttonsLayout.createSequentialGroup()
                .addGroup(buttonsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelect)
                    .addComponent(btnCancel))
        );
        
        this.pack();

    }
    
    private class Controller implements ActionListener, ListSelectionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            /**
             * Select button
             */
            if (ae.getSource() == btnSelect){
                SelectTreatmentDialog.this.setVisible(false);
            }
            /**
             * Cancel button
             */
            else if (ae.getSource() == btnCancel){
                tableModel.setData(new Treatment("Temp")); // "reset" table
                SelectTreatmentDialog.this.setVisible(false);
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            /**
             * Update table with selected treatment from the list.
             */
            if (lse.getSource() == treatmentsList){
                int index = treatmentsList.getSelectedIndex();
                if (index >= 0){
                    Treatment t = treatments.get(index);
                    if (t.getName().equals(treatmentsList.getSelectedValue())){
                        tableModel.setData(t);
                    }
                }
            }
        }
    }
}
/**
 * @author Martin
 */

package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import carletti.service.Service;

public class ShowDoneDialog extends JFrame
{
    
    private Dimension minsize = new Dimension(600, 400);
    private Service service;
    private Controller btnCtrl;
    private JButton btnOk;
    private JScrollPane scrollPane;
    private ShowDoneDialogTableModel tableModel;
    private JTable doneTable;
    
    public ShowDoneDialog()
    {
        
        btnCtrl = new Controller();
        service = Service.getInstance();
        this.setMinimumSize(minsize);

        tableModel = new ShowDoneDialogTableModel();
        doneTable = new JTable(tableModel);

        scrollPane = new JScrollPane(doneTable);
        scrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        btnOk = new JButton("OK");
        btnOk.addActionListener(btnCtrl);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                    .addGap(0))
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnOk)
                    .addContainerGap(527, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnOk)
                    .addContainerGap())
        );
        
        getContentPane().setLayout(groupLayout);

        this.setVisible(true);
        
    }

    private class Controller implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            if (ae.getSource().equals(btnOk))
            {
                ShowDoneDialog.this.setVisible(false);
            }
        }
    }       
}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class ShowDoneDialogTableModel extends AbstractTableModel
{
    private Service service;
    // Column headers.
    private String[] coloumnNames =
    { "ID", "Name", "Subtreatment", "Number of Subtreatments" };
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State done = State.DONE;

    public ShowDoneDialogTableModel()
    {
        service = Service.getInstance();
        newData = service.getSubProducts(done);
    }

    @Override
    public int getColumnCount()
    {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return newData.size();
    }

    @Override
    public String getColumnName(int col)
    {
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        SubProduct sp = newData.get(row);
        Object[] value =
        {

        new Integer(sp.getId()), sp.getName(),
                sp.getCurrentSubTreatment().getName(),
                sp.getSubtreatments().size() };
        return value[col];
    }

    @Override
    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    public SubProduct selctedSubProduct(int selectedRow)
    {
        if ( selectedRow >= 0)
        {
            return newData.get(selectedRow);
        }
        else
            return null;
    }

    public void updateData()
    {
        newData = service.getSubProducts(done);
    }
}
/**
 * @author Martin
 */

package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import carletti.service.Service;


public class ShowWastedDialog extends JFrame
{
    
    private Dimension minsize = new Dimension(600, 400);
    private Service service;
    private Controller btnCtrl;
    private JButton btnOk;
    private JScrollPane scrollPane;
    private ShowWastedDialogTableModel tableModel;
    private JTable wastedTable;
    
    public ShowWastedDialog()
    {
    btnCtrl = new Controller();
    service = Service.getInstance();
    this.setMinimumSize(minsize);

    tableModel = new ShowWastedDialogTableModel();
    wastedTable = new JTable(tableModel);

    scrollPane = new JScrollPane(wastedTable);
    scrollPane
            .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    getContentPane().add(scrollPane, BorderLayout.CENTER);

    btnOk = new JButton("OK");
    btnOk.addActionListener(btnCtrl);

    GroupLayout groupLayout = new GroupLayout(getContentPane());
    groupLayout.setHorizontalGroup(
        groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup()
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                .addGap(0))
            .addGroup(groupLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnOk)
                .addContainerGap(527, Short.MAX_VALUE))
    );
    groupLayout.setVerticalGroup(
        groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(groupLayout.createSequentialGroup()
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(btnOk)
                .addContainerGap())
    );
    
    getContentPane().setLayout(groupLayout);

    this.setVisible(true);
    
}

private class Controller implements ActionListener
{
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource().equals(btnOk))
        {
            ShowWastedDialog.this.setVisible(false);
        }
    }
}


}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class ShowWastedDialogTableModel extends AbstractTableModel
{
    private Service service;
    // Column headers.
    private String[] coloumnNames =
    { "ID", "Name", "Subtreatment", "Number of Subtreatments" };
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State wasted = State.WASTE;

    public ShowWastedDialogTableModel()
    {
        service = Service.getInstance();
        newData = service.getSubProducts(wasted);
    }

    public int getColumnCount()
    {
        return coloumnNames.length;
    }

    public int getRowCount()
    {
        return newData.size();
    }

    public String getColumnName(int col)
    {
        return coloumnNames[col];
    }

    public Object getValueAt(int row, int col)
    {
        SubProduct sp = newData.get(row);
        Object[] value =
        {

        new Integer(sp.getId()), sp.getName(),
                sp.getCurrentSubTreatment().getName(),
                sp.getSubtreatments().size() };
        return value[col];
    }

    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    public SubProduct selctedSubProduct(int selectedRow)
    {
        if ( selectedRow >= 0)
        {
            return newData.get(selectedRow);
        }
        else
            return null;
    }

    public void updateData()
    {
        newData = service.getSubProducts(wasted);
    }
}
package carletti.gui.dialogs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carletti.model.State;
import carletti.model.SubProduct;

/**
 * @group Lars Nielsen, Malik Lasse Lund, Martin RÀn Bundgaard
 * @class SubProductDialog
 * @author Lars Nielsen
 */
public class SubProductDialog extends JDialog{

    private SubProduct sub; //holds the current sub product which is wanted for inspection
    
    /*
     *GUI components 
     */
    private JPanel panel;
    private JLabel labID, labName, labState, labProduct;
    private JTextField txfID, txfName, txfState, txfProd;
    private JButton btnOK;
    private Controller btnCtrl;
    
    public SubProductDialog(SubProduct sub){
        
        this.sub = sub;
        btnCtrl = new Controller();
        
        //Preferences for the dialog 
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
        
        //GUI components and their preferences
        panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new GridLayout(6, 2, 20, 20));
        
        labID = new JLabel();
        labID.setText("ID:");
        panel.add(labID);
        
        txfID = new JTextField();
        txfID.setEditable(false); //User should not be able to change the info from heir
        panel.add(txfID);
        
        labName = new JLabel();
        labName.setText("Name:");
        panel.add(labName);
        
        txfName = new JTextField();
        txfName.setEditable(false);
        panel.add(txfName);
        
        labState = new JLabel();
        labState.setText("State:");
        panel.add(labState);
        
        txfState = new JTextField();
        txfState.setEditable(false);
        panel.add(txfState);
        
        labProduct = new JLabel();
        labProduct.setText("Product");
        panel.add(labProduct);
        
        txfProd = new JTextField();
        txfProd.setEditable(false);
        panel.add(txfProd);
        
        btnOK = new JButton();
        btnOK.setText("OK");
        btnOK.addActionListener(btnCtrl);
        panel.add(btnOK);
        
        setInfo();
        this.pack();
    }
    
    //Sets the info for the JTextFields 
    private void setInfo(){
        txfID.setText(Integer.toString(sub.getId()));
        txfName.setText(sub.getName());
        checkAndSetState();
        txfProd.setText(sub.getProduct().getName());
        
        
    }
    
    //Sets the info for the txfState
    private void checkAndSetState(){
        if(sub.getState() == State.DONE){
            txfState.setText("Done");
        }
        else if(sub.getState() == State.TREATMENT){
            txfState.setText("Treatment");
        }
        else if(sub.getState() == State.DRYING){
            txfState.setText("Drying");
        }
        else if(sub.getState() == State.WASTE){
            txfState.setText("Waste");
        }
    }
    
    //Allows to change the visibility of the dialog in the Controller
    private void thisSetVisible(boolean visiblity){
        this.setVisible(visiblity);
    }
    
    /*
     * Keeps track of the actions on the buttons
     */
    private class Controller implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            // TODO Auto-generated method stub
            if(ae.getSource().equals(btnOK)){
                thisSetVisible(false);
            }
        }
        
    }
}
package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

/**
 * @group Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 * @class WasteSubProduct
 * @author Lars Nielsen
 *
 */
public class WasteSubProduct extends JDialog{
    
    private Service service; //Holds a singelton of the service class
    
    //GUI Components
    private JPanel infoPanel, btnPanel;
    private JButton btnWaste;
    private JButton btnCan;
    private JLabel lblId;
    private JTextField txfID;
    private JLabel lblName;
    private JLabel lblNewLabel;
    private JLabel lblProduct;
    private JTextField txfName;
    private JTextField txfState;
    private JTextField txfProduct;
    
    private SubProduct sub; //Holds the SubProduct, which can be wasted
    
    
    private Controller btnCtrl; //Instance of the controller class
    
    
    public WasteSubProduct(SubProduct sub) {
        
        service = Service.getInstance(); 
        this.sub = sub;
        btnCtrl = new Controller();
        
        //Generel Prefernec for the Dialog
        setTitle("Waste Subproduct");
        setModal(true);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        //GUI Components and their preferens 
        infoPanel = new JPanel();
        getContentPane().add(infoPanel, BorderLayout.CENTER);
        GridBagLayout gbl_infoPanel = new GridBagLayout();
        gbl_infoPanel.columnWidths = new int[]{0, 0, 0};
        gbl_infoPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
        gbl_infoPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_infoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        infoPanel.setLayout(gbl_infoPanel);
        
        lblId = new JLabel("ID:");
        GridBagConstraints gbc_lblId = new GridBagConstraints();
        gbc_lblId.insets = new Insets(0, 0, 5, 5);
        gbc_lblId.anchor = GridBagConstraints.EAST;
        gbc_lblId.gridx = 0;
        gbc_lblId.gridy = 0;
        infoPanel.add(lblId, gbc_lblId);
        
        txfID = new JTextField();
        txfID.setEditable(false);
        GridBagConstraints gbc_txfID = new GridBagConstraints();
        gbc_txfID.insets = new Insets(0, 0, 5, 0);
        gbc_txfID.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfID.gridx = 1;
        gbc_txfID.gridy = 0;
        infoPanel.add(txfID, gbc_txfID);
        txfID.setColumns(10);
        
        lblName = new JLabel("NAME:");
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.anchor = GridBagConstraints.EAST;
        gbc_lblName.insets = new Insets(0, 0, 5, 5);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 1;
        infoPanel.add(lblName, gbc_lblName);
        
        txfName = new JTextField();
        txfName.setEditable(false);
        GridBagConstraints gbc_txfName = new GridBagConstraints();
        gbc_txfName.insets = new Insets(0, 0, 5, 0);
        gbc_txfName.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfName.gridx = 1;
        gbc_txfName.gridy = 1;
        infoPanel.add(txfName, gbc_txfName);
        txfName.setColumns(10);
        
        lblNewLabel = new JLabel("STATE:\n");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 2;
        infoPanel.add(lblNewLabel, gbc_lblNewLabel);
        
        txfState = new JTextField();
        txfState.setEditable(false);
        GridBagConstraints gbc_txfState = new GridBagConstraints();
        gbc_txfState.insets = new Insets(0, 0, 5, 0);
        gbc_txfState.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfState.gridx = 1;
        gbc_txfState.gridy = 2;
        infoPanel.add(txfState, gbc_txfState);
        txfState.setColumns(10);
        
        lblProduct = new JLabel("PRODUCT");
        GridBagConstraints gbc_lblProduct = new GridBagConstraints();
        gbc_lblProduct.anchor = GridBagConstraints.EAST;
        gbc_lblProduct.insets = new Insets(0, 0, 0, 5);
        gbc_lblProduct.gridx = 0;
        gbc_lblProduct.gridy = 3;
        infoPanel.add(lblProduct, gbc_lblProduct);
        
        txfProduct = new JTextField();
        GridBagConstraints gbc_txfProduct = new GridBagConstraints();
        gbc_txfProduct.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfProduct.gridx = 1;
        gbc_txfProduct.gridy = 3;
        infoPanel.add(txfProduct, gbc_txfProduct);
        txfProduct.setColumns(10);
        
        btnPanel = new JPanel();
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        btnPanel.setLayout(new GridLayout(1, 2, 0, 0));
        
        btnWaste = new JButton("Waste");
        btnWaste.addActionListener(btnCtrl);
        btnPanel.add(btnWaste);
        
        btnCan = new JButton("Cancel");
        btnCan.addActionListener(btnCtrl);
        btnPanel.add(btnCan);
        
        setInfo();
        checkAndSetState();
        
        this.pack();
    }
    
    /**
     * Allows to set the visibility of the dialog, in the Controller class 
     */
    private void desposeDialog(boolean despose){
        this.setVisible(despose);
    }
    
    /**
     * Sets the text of the JTextFields, so one know which SubProduct you are wasting 
     */
    private void setInfo(){
        txfID.setText(Integer.toString(sub.getId()));
        txfName.setText(sub.getName());
        checkAndSetState();
        txfProduct.setText(sub.getProduct().getName());
    }
    
    /**
     * Checks the state of the subproduct and sets the state of the txfState, to the current state
     */
    private void checkAndSetState(){
        if(sub.getState() == State.DONE){
            txfState.setText("Done");
        }
        else if(sub.getState() == State.TREATMENT){
            txfState.setText("Treatment");
        }
        else if(sub.getState() == State.DRYING){
            txfState.setText("Drying");
        }
        else if(sub.getState() == State.WASTE){
            txfState.setText("Waste");
        }
    }
    
    
    /**
     * 
     * The controller class, is controlling which actions is 
     *
     */
    private class Controller implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource().equals(btnWaste)){
                service.changeState(sub, State.WASTE);
                sub.setPosition(null);
                desposeDialog(false);
            }
            else if(ae.getSource().equals(btnCan)){
                desposeDialog(false);
            }
            
        }
        
    }

}
package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import carletti.gui.dialogs.CreateNewProductDialog;
import carletti.gui.dialogs.NewSubProductDialog;
import carletti.gui.dialogs.NextSubTreatmentDialog;
import carletti.gui.dialogs.ShowDoneDialog;
import carletti.gui.dialogs.ShowWastedDialog;
import carletti.gui.dialogs.ShowWastedDialogTableModel;
import carletti.gui.dialogs.SubProductDialog;
import carletti.gui.dialogs.WasteSubProduct;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.table.TableColumn;

/**
 * @group Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 * @class MainFrame
 * @author Lars Nielsen
 *
 */
public class MainFrame extends JFrame {
    
    private Service service; //Holds singelton instance of service

    //GUI components
    private JPanel buttonsPanel;
    private JScrollPane subProductsScrollPane;
    private JButton btnNewSubProduct;
    private JButton btnInfo;
    private JButton btnWaste;
    private JTable subProductTable;
    private NewSubProductTableModel subProductTableModel;
    private Dimension minimumSize = new Dimension(1000, 400);
    private Dimension btnMinSize = new Dimension(20, 180);
    private JButton btnNewProduct;
    private JButton btnProductInfo;
    private JButton btnTreatment;
    private JButton btnShowSubTreatment;
    private JButton btnShowDone;
    private JButton btnShowWasted;

    //Button controller
    private Controller btnCtrl;
    
    public MainFrame() {
        
        service = Service.getInstance();

        btnCtrl = new Controller();
        
        //Frame preferences 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        this.setTitle("Carletti");
        this.setSize(minimumSize);
        this.setLocation(new Point(100, 50));
        buttonsPanel = new JPanel();
        getContentPane().add(buttonsPanel, BorderLayout.EAST);

        //GUI Components preferences 
        btnInfo = new JButton("Info");
        btnInfo.setMinimumSize(btnMinSize);
        btnInfo.addActionListener(btnCtrl);

        btnWaste = new JButton("Waste");
        btnWaste.setMinimumSize(btnMinSize);
        btnWaste.addActionListener(btnCtrl);

        btnProductInfo = new JButton("Product Info");
        btnProductInfo.addActionListener(btnCtrl);

        btnNewSubProduct = new JButton("New subproduct");
        btnNewSubProduct.setMinimumSize(btnMinSize);
        btnNewSubProduct.addActionListener(btnCtrl);

        btnNewProduct = new JButton("New Product");
        
        btnTreatment = new JButton("Treatment");
        btnTreatment.addActionListener(btnCtrl);
        
        btnShowSubTreatment = new JButton("Show Subtreatment");
        btnShowSubTreatment.addActionListener(btnCtrl);
        
        btnShowDone = new JButton("Show Done");
        btnShowDone.addActionListener(btnCtrl);
        
        btnShowWasted = new JButton("Show Wasted");
        btnShowWasted.addActionListener(btnCtrl);
        
        GroupLayout gl_buttonsPanel = new GroupLayout(buttonsPanel);
        gl_buttonsPanel.setHorizontalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addComponent(btnInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnWaste, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnProductInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnNewProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnTreatment, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowSubTreatment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNewSubProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowDone, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowWasted, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );
        gl_buttonsPanel.setVerticalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_buttonsPanel.createSequentialGroup()
                    .addComponent(btnInfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnWaste, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnProductInfo)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewSubProduct, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewProduct)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowSubTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowDone)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowWasted)
                    .addGap(107))
        );
        buttonsPanel.setLayout(gl_buttonsPanel);
        btnNewProduct.addActionListener(btnCtrl);

        // subProductList = new JList();
        // subProductList.setListData(Service.getAllNotWastedSubProducts().toArray());
        // subProductList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        subProductTableModel = new NewSubProductTableModel();
        subProductTable = new JTable(subProductTableModel);
        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subProductTable.setDefaultRenderer(subProductTableModel.getColumnClass(0), new HighlighterCellRenderer());

        subProductsScrollPane = new JScrollPane(subProductTable);
        subProductsScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(subProductsScrollPane, BorderLayout.CENTER);

        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initColumnSizes(subProductTable);
        
        // --- Malik Lund ---
        Thread t = new Thread(new UpdaterThread(this));
        t.start();
        // ------------------

        this.setVisible(true);

    }
    
    /**
     * Hardcode some default column sizes.
     * @author Malik Lund
     * @param table
     */
    private void initColumnSizes(JTable table) {
        NewSubProductTableModel model = (NewSubProductTableModel)table.getModel();
        TableColumn column = null;
        for (int i = 0; i < model.getColumnCount(); i++){
            column = table.getColumnModel().getColumn(i);
            if (i == 1){
                column.setPreferredWidth(125);
            }
            else if (i == 2){
                column.setPreferredWidth(40);
            }
            else if (i == 3){
                column.setPreferredWidth(40);
            }
        }
    }

    /*
     * @author Malik Lund
     */
    public synchronized void updateList() {
        int selection = subProductTable.getSelectedRow();
        subProductTableModel.updateData();
        subProductTableModel.fireTableDataChanged();
        subProductTable.changeSelection(selection, 0, false, false);
    }

    private class Controller implements ActionListener {
        public void actionPerformed(ActionEvent ae) {

            /*
             * Info button
             */
            if (ae.getSource().equals(btnInfo)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                } else {
                    SubProductDialog spd = new SubProductDialog(sp);
                    spd.setLocationRelativeTo(MainFrame.this);
                    spd.setVisible(true);
                }
            }
            /*
             * New subproduct button
             */
            else if (ae.getSource().equals(btnNewSubProduct)) {
                NewSubProductDialog nspd = new NewSubProductDialog();
                nspd.setLocationRelativeTo(MainFrame.this);
                nspd.setVisible(true);
                updateList();
            } 
            /*
             * New product button
             */
            else if (ae.getSource().equals(btnNewProduct)) {
                CreateNewProductDialog newProductDialog = new CreateNewProductDialog();
                newProductDialog.setLocationRelativeTo(MainFrame.this);
                newProductDialog.setVisible(true);
            } 
            /*
             * Waste button
             */
            else if (ae.getSource().equals(btnWaste)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }

                else {
                    WasteSubProduct wsp = new WasteSubProduct(sp);
                    wsp.setLocationRelativeTo(MainFrame.this);
                    wsp.setVisible(true);
                    updateList();
                }

            }
            /*
             * Treatment button
             */
            else if (ae.getSource().equals(btnTreatment)){
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) 
                {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }
                else
                {
                    service.changeState(sp, State.TREATMENT);
                    sp.setPosition(null);
                }
                updateList();
            }
            /*
             * Product info button
             */
            else if (ae.getSource().equals(btnProductInfo)) {
                ShowProductFrame spf = new ShowProductFrame();
                spf.setLocationRelativeTo(MainFrame.this);
                spf.setVisible(true);
            }
            
            /*
             * Show subtreatment button
             */
            else if (ae.getSource().equals(btnShowSubTreatment))
            {
                NextSubTreatmentDialog ntd = new NextSubTreatmentDialog();
                ntd.setLocationRelativeTo(MainFrame.this);
                ntd.setVisible(true);
            }
            /*
             * Show done button
             */
            else if (ae.getSource().equals(btnShowDone))
            {
                ShowDoneDialog sdd = new ShowDoneDialog();
                sdd.setLocationRelativeTo(MainFrame.this);
                sdd.setVisible(true);
            }
            /*
             * Show wasted button
             */
            else if (ae.getSource().equals(btnShowWasted))
            {
                ShowWastedDialog swd = new ShowWastedDialog();
                swd.setLocationRelativeTo(MainFrame.this);
                swd.setVisible(true);
                
            }
        }
    }
    
    /**
     * Custom cell renderer that colors the "time remaining" cell
     * white, green, yellow or red depending on the time remaining
     * until max drying time is exceeded.
     * 
     * @author Malik Lund
     */
    private class HighlighterCellRenderer extends DefaultTableCellRenderer{
        private DefaultTableCellRenderer defaultRenderer;
        
        public HighlighterCellRenderer(){
            super();
            defaultRenderer = new DefaultTableCellRenderer();
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            SubProduct subProducts = subProductTableModel.getSubProduct(row);
            
            long currentTime = System.currentTimeMillis();
            long minTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMin();
            long optimalTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryPrime();
            long maxTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMax();
            
            // Set color
            if (minTime < currentTime && optimalTime > currentTime && column == 0){
                comp.setBackground(Color.green);
            } 
            else if (optimalTime < currentTime && maxTime > currentTime && column == 0){
                comp.setBackground(Color.yellow);
            }
            else if (currentTime > maxTime && column == 0) {
                comp.setBackground(Color.red);
            } else {
                // For reasons I don't understand and don't have time to look into
                // it is necessary to have two different renderers. The color change is
                // apparently persistent.
                return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
            
            return comp;
        }
    }
}
package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import carletti.gui.dialogs.CreateNewProductDialog;
import carletti.gui.dialogs.NewSubProductDialog;
import carletti.gui.dialogs.NextSubTreatmentDialog;
import carletti.gui.dialogs.ShowDoneDialog;
import carletti.gui.dialogs.ShowWastedDialog;
import carletti.gui.dialogs.ShowWastedDialogTableModel;
import carletti.gui.dialogs.SubProductDialog;
import carletti.gui.dialogs.WasteSubProduct;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import javax.swing.table.TableColumn;


public class MainFrameTabbed extends JFrame {
    private Service service;

    private JPanel buttonsPanel;
    private JScrollPane subProductsScrollPane, subProductsTreatmentScrollPane;
    private JButton btnNewSubProduct;
    private JButton btnInfo;
    private JButton btnWaste;
    private JTabbedPane tabbedPane;
    private JTable subProductTable, subProductsTreatmentTable;
    private NewSubProductTableModelTabbed subProductTableModel, subProductsTreatmentTableModel;

    private Controller btnCtrl;

    private Dimension minimumSize = new Dimension(1000, 400);
    private Dimension btnMinSize = new Dimension(20, 180);
    private JButton btnNewProduct;
    private JButton btnProductInfo;
    private JButton btnTreatment;
    private JButton btnShowSubTreatment;
    private JButton btnShowDone;
    private JButton btnShowWasted;

    public MainFrameTabbed() {
        service = Service.getInstance();

        btnCtrl = new Controller();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        this.setTitle("Carletti");
        this.setSize(minimumSize);
        this.setLocation(new Point(100, 50));
        buttonsPanel = new JPanel();
        getContentPane().add(buttonsPanel, BorderLayout.EAST);

        btnInfo = new JButton("Info");
        btnInfo.setMinimumSize(btnMinSize);
        btnInfo.addActionListener(btnCtrl);

        btnWaste = new JButton("Waste");
        btnWaste.setMinimumSize(btnMinSize);
        btnWaste.addActionListener(btnCtrl);

        btnProductInfo = new JButton("Product Info");
        btnProductInfo.addActionListener(btnCtrl);

        btnNewSubProduct = new JButton("New subproduct");
        btnNewSubProduct.setMinimumSize(btnMinSize);
        btnNewSubProduct.addActionListener(btnCtrl);

        btnNewProduct = new JButton("New Product");
        
        btnTreatment = new JButton("Treatment");
        btnTreatment.addActionListener(btnCtrl);
        
        btnShowSubTreatment = new JButton("Show Subtreatment");
        btnShowSubTreatment.addActionListener(btnCtrl);
        
        btnShowDone = new JButton("Show Done");
        btnShowDone.addActionListener(btnCtrl);
        
        btnShowWasted = new JButton("Show Wasted");
        btnShowWasted.addActionListener(btnCtrl);
        
        GroupLayout gl_buttonsPanel = new GroupLayout(buttonsPanel);
        gl_buttonsPanel.setHorizontalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addComponent(btnInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnWaste, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnProductInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnNewProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnTreatment, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowSubTreatment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNewSubProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowDone, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowWasted, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );
        gl_buttonsPanel.setVerticalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_buttonsPanel.createSequentialGroup()
                    .addComponent(btnInfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnWaste, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnProductInfo)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewSubProduct, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewProduct)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowSubTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowDone)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowWasted)
                    .addGap(107))
        );
        buttonsPanel.setLayout(gl_buttonsPanel);
        btnNewProduct.addActionListener(btnCtrl);
        
        tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // --- Drying table ---
        subProductTableModel = new NewSubProductTableModelTabbed(State.DRYING);
        subProductTable = new JTable(subProductTableModel);
        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subProductTable.setDefaultRenderer(subProductTableModel.getColumnClass(0), new HighlighterCellRenderer());

        subProductsScrollPane = new JScrollPane(subProductTable);
        subProductsScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//      getContentPane().add(subProductsScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Drying", subProductsScrollPane);

        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initColumnSizes(subProductTable);
        
        // --- In treatment table ---
        subProductsTreatmentTableModel = new NewSubProductTableModelTabbed(State.TREATMENT);
        subProductsTreatmentTable = new JTable(subProductsTreatmentTableModel);
        subProductsTreatmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subProductsTreatmentTable.setDefaultRenderer(subProductTableModel.getColumnClass(0), new HighlighterCellRenderer());
        
        subProductsTreatmentScrollPane = new JScrollPane(subProductsTreatmentTable);
        subProductsTreatmentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        tabbedPane.addTab("In treatment", subProductsTreatmentScrollPane);
        
        // --- Malik Lund start---
        Thread t = new Thread(new UpdaterThreadForTabbedMain(this));
        t.start();
        // -------------- end ----

        this.setVisible(true);

    }
    
    /**
     * Hardcode some default column sizes.
     * @author Malik Lund
     * @param table
     */
    private void initColumnSizes(JTable table) {
        NewSubProductTableModelTabbed model = (NewSubProductTableModelTabbed)table.getModel();
        TableColumn column = null;
        for (int i = 0; i < model.getColumnCount(); i++){
            column = table.getColumnModel().getColumn(i);
            if (i == 1){
                column.setPreferredWidth(125);
            }
            else if (i == 2){
                column.setPreferredWidth(40);
            }
            else if (i == 3){
                column.setPreferredWidth(40);
            }
        }
    }

    /*
     * @author Malik Lund
     */
    public synchronized void updateList() {
        int selection = subProductTable.getSelectedRow();
        subProductTableModel.updateData();
        subProductTableModel.fireTableDataChanged();
        subProductTable.changeSelection(selection, 0, false, false);
        
        updateTable(subProductsTreatmentTable);
    }
    
    private synchronized void updateTable(JTable table){
        int selection = table.getSelectedRow();
        NewSubProductTableModelTabbed tableModel = (NewSubProductTableModelTabbed) table.getModel();
        tableModel.updateData();
        tableModel.fireTableDataChanged();
        if (selection >= 0 && selection < tableModel.getColumnCount()){
            table.changeSelection(selection, 0, false, false);
        }
    }

    private class Controller implements ActionListener {
        public void actionPerformed(ActionEvent ae) {

            if (ae.getSource().equals(btnInfo)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                } else {
                    SubProductDialog spd = new SubProductDialog(sp);
                    spd.setLocationRelativeTo(MainFrameTabbed.this);
                    spd.setVisible(true);
                }
            } else if (ae.getSource().equals(btnNewSubProduct)) {
                NewSubProductDialog nspd = new NewSubProductDialog();
                nspd.setLocationRelativeTo(MainFrameTabbed.this);
                nspd.setVisible(true);
                updateList();
            } else if (ae.getSource().equals(btnNewProduct)) {
                CreateNewProductDialog newProductDialog = new CreateNewProductDialog();
                newProductDialog.setLocationRelativeTo(MainFrameTabbed.this);
                newProductDialog.setVisible(true);
            } else if (ae.getSource().equals(btnWaste)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }

                else {
                    WasteSubProduct wsp = new WasteSubProduct(sp);
                    wsp.setLocationRelativeTo(MainFrameTabbed.this);
                    wsp.setVisible(true);
                    updateList();
                }

            }
            
            else if (ae.getSource().equals(btnTreatment)){
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) 
                {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }
                else
                {
                    service.changeState(sp, State.TREATMENT);
                }
                updateList();
            }

            else if (ae.getSource().equals(btnProductInfo)) {
                ShowProductFrame spf = new ShowProductFrame();
                spf.setLocationRelativeTo(MainFrameTabbed.this);
                spf.setVisible(true);
            }
            
            else if (ae.getSource().equals(btnShowSubTreatment))
            {
                NextSubTreatmentDialog ntd = new NextSubTreatmentDialog();
                ntd.setLocationRelativeTo(MainFrameTabbed.this);
                ntd.setVisible(true);
            }
            else if (ae.getSource().equals(btnShowDone))
            {
                ShowDoneDialog sdd = new ShowDoneDialog();
                sdd.setLocationRelativeTo(MainFrameTabbed.this);
                sdd.setVisible(true);
            }
            else if (ae.getSource().equals(btnShowWasted))
            {
                ShowWastedDialog swd = new ShowWastedDialog();
                swd.setLocationRelativeTo(MainFrameTabbed.this);
                swd.setVisible(true);
                
            }
        }
    }
    
    /**
     * Custom cell renderer that colors the "time remaining" cell
     * white, green, yellow or red depending on the time remaining
     * until max drying time is exceeded.
     * 
     * @author Malik Lund
     */
    private class HighlighterCellRenderer extends DefaultTableCellRenderer{
        private DefaultTableCellRenderer defaultRenderer;
        
        public HighlighterCellRenderer(){
            super();
            defaultRenderer = new DefaultTableCellRenderer();
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            SubProduct subProducts = subProductTableModel.getSubProduct(row);
            
            long currentTime = System.currentTimeMillis();
            long minTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMin();
            long optimalTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryPrime();
            long maxTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMax();
            
            // Set color
            if (minTime < currentTime && optimalTime > currentTime && column == 0){
                comp.setBackground(Color.green);
            } 
            else if (optimalTime < currentTime && maxTime > currentTime && column == 0){
                comp.setBackground(Color.yellow);
            }
            else if (currentTime > maxTime && column == 0) {
                comp.setBackground(Color.red);
            } else {
                // For reasons I don't understand and don't have time to look into
                // it is necessary to have two different renderers. The color change is
                // apparently persistent.
                return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
            
            return comp;
        }
    }
}
/**
 * @group Lars Nielsen, Malik Lasse Lund, Martin RÀnn Bungaard
 * @class NewSubProductTableModel
 * @author Lars Nielsen
 */
package carletti.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.dao.JpaDao;
import carletti.model.LongToStringParser;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class NewSubProductTableModel extends AbstractTableModel{
    private Service service;
    // Column headers.
    private String[] coloumnNames = {"Time left", "Started on", "ID", "Position", "Name", "State", "Subtreatment", "Subtreatment #"};
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State state = State.DRYING;
        
    public NewSubProductTableModel(){
        service = Service.getInstance();
        newData = service.getSubProducts(state);
    }

    @Override
    public int getColumnCount() {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount() {
        return newData.size();
    }

    @Override
    public String getColumnName(int col){
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        SubProduct sp = newData.get(row);
        Object[] value = {
                LongToStringParser.parseLongToString(sp.timeLeft()), sp.getTime(sp.getTimeAdded()), 
                new Integer(sp.getId()), sp.getPosition().getPosID(), sp.getName(), 
                sp.getState(), sp.getCurrentSubTreatment().getName(), 
                (sp.getCurrentSubTreatmentIndex() + 1) + "/" + sp.getSubtreatments().size() 
        };
        return value[col];
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
    
    public SubProduct selctedSubProduct(int selectedRow){
        if(selectedRow >= 0){
            return newData.get(selectedRow);
        }
        else{
            return null;
        }
        
    }
    
    /**
     * @author Malik Lund
     * @return
     */
    public SubProduct getSubProduct(int row){
        return newData.get(row);
    }

    public void updateData() {
        newData = service.getSubProducts(state);
    }
    

}
/**
 * @group Lars Nielsen, Malik Lasse Lund, Martin RÀnn Bungaard
 * @class NewSubProductTableModel
 * @author Lars Nielsen
 */
package carletti.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.dao.JpaDao;
import carletti.model.LongToStringParser;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class NewSubProductTableModelTabbed extends AbstractTableModel{
    private Service service;
    // Column headers.
    private String[] coloumnNames = {"Time left", "Started on", "ID", "Position", "Name", "State", "Subtreatment", "Subtreatment #"};
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State state;
        
    public NewSubProductTableModelTabbed(State state){
        service = Service.getInstance();
        this.state = state;
        newData = service.getSubProducts(state);
    }

    @Override
    public int getColumnCount() {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount() {
        return newData.size();
    }

    @Override
    public String getColumnName(int col){
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        SubProduct sp = newData.get(row);
        Object[] value = {
                LongToStringParser.parseLongToString(sp.timeLeft()), sp.getTime(sp.getTimeAdded()), 
                new Integer(sp.getId()), sp.getPosition().getPosID(), sp.getName(), 
                sp.getState(), sp.getCurrentSubTreatment().getName(), 
                (sp.getCurrentSubTreatmentIndex() + 1) + "/" + sp.getSubtreatments().size() 
        };
        return value[col];
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
    
    public SubProduct selctedSubProduct(int selectedRow){
        if(selectedRow >= 0){
            return newData.get(selectedRow);
        }
        else{
            return null;
        }
        
    }
    
    /**
     * @author Malik Lund
     * @return
     */
    public SubProduct getSubProduct(int row){
        return newData.get(row);
    }

    public void updateData() {
        newData = service.getSubProducts(state);
    }
    

}
package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import carletti.service.Service;
import carletti.dao.JpaDao;

public class ShowProductFrame extends JDialog
{
    private Service service;
    private JPanel contentPane;
    private JPanel panel;
    private JButton btnOk;
    private JSplitPane splitPane;
    private JTextPane textPane;
    private JScrollPane scrollPane;
    private JScrollPane screooPane2;
    private JList list;
    private Dimension minimumSize = new Dimension(400, 500);
    
    private Controller btnCtrl;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    ShowProductFrame frame = new ShowProductFrame();
                    frame.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ShowProductFrame()
    {
        service = Service.getInstance();
        
        btnCtrl = new Controller();
        this.setMinimumSize(minimumSize);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);
        
        btnOk = new JButton("OK");
        panel.add(btnOk);
        btnOk.addActionListener(btnCtrl);
        
        splitPane = new JSplitPane();
        contentPane.add(splitPane, BorderLayout.CENTER);
        
        textPane = new JTextPane();
        textPane.setEditable(false);
        splitPane.setRightComponent(textPane);
        
        list = new JList();
        contentPane.add(list, BorderLayout.NORTH);
        list.setListData(service.getProducts().toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                        
                if (e.getValueIsAdjusting())
                    updateTextPane();
            }
            
        });
        
        scrollPane = new JScrollPane(list);
        splitPane.setLeftComponent(scrollPane);
    
        screooPane2 = new JScrollPane(textPane);
        splitPane.setRightComponent(screooPane2);
        
    }
    
    public void updateTextPane()
    {
        int index = list.getSelectedIndex();
        if (index >= 0)
            textPane.setText(service.getProducts().get(list.getSelectedIndex()).getName() + "\n" +
                    service.getProducts().get(list.getSelectedIndex()).getDescription() + "\n" + "Treatment:" + "\n" +
                    service.getProducts().get(list.getSelectedIndex()).getTreatment());
    }
    
    private class Controller implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            
            if(ae.getSource().equals(btnOk)){
                ShowProductFrame.this.setVisible(false);
                }
        }
    }
}
package carletti.gui;

import carletti.gui.dialogs.NextSubTreatmentDialog;

/**
 * Continuously updates the list of SubProducts in MainFrame
 * Obs. keeps running until window is closed.
 * @author Malik Lund
 *
 */
public class UpdaterThread implements Runnable {
    private MainFrame mainFrame;
    private NextSubTreatmentDialog nextSubTreatmentDialog;
    
    public UpdaterThread(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }
    
    public UpdaterThread(NextSubTreatmentDialog nextSubTreatmentDialog)
    {
        this.nextSubTreatmentDialog =  nextSubTreatmentDialog;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true){
            mainFrame.updateList();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
package carletti.gui;

import carletti.gui.dialogs.NextSubTreatmentDialog;

/**
 * Continuously updates the list of SubProducts in MainFrame
 * Obs. keeps running until window is closed.
 * @author Malik Lund
 *
 */
public class UpdaterThreadForTabbedMain implements Runnable {
    private MainFrameTabbed mainFrame;
    private NextSubTreatmentDialog nextSubTreatmentDialog;
    
    public UpdaterThreadForTabbedMain(MainFrameTabbed mainFrame){
        this.mainFrame = mainFrame;
    }
    
    public UpdaterThreadForTabbedMain(NextSubTreatmentDialog nextSubTreatmentDialog)
    {
        this.nextSubTreatmentDialog =  nextSubTreatmentDialog;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true){
            mainFrame.updateList();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
package carletti.model;

/**
 * 
 * @author Malik Lund
 *
 */
public class LongToStringParser {
    
    /**
     * Converts a time given in milliseconds into a string
     * of the form [+/-]DDdHHhMMmSSs where DD represents days, HH represents hours,
     * MM represents minutes and SS represents seconds. 
     * All numbers get padded with a zero if they only consist of a single digit.
     * @param time
     * @return
     */
    public static String parseLongToString(long time){
        boolean negative = false;
        int days, hours, minutes, seconds;
        
        if (time < 0){
            negative = true;
            time = - time;
        }
        
        days = (int) (time / (1000 * 60 * 60 * 24));
        time = time - days * 1000 * 60 * 60 * 24;
        
        hours = (int) (time / (1000 * 60 * 60));
        time = time - hours * 1000 * 60 * 60;
        
        minutes = (int) (time / (1000 * 60));
        time = time - minutes * 1000 * 60;
        
        seconds = (int) (time / (1000));
        
        StringBuffer buffer = new StringBuffer();
        if (negative){
            buffer.append("+");
        } else {
            buffer.append("-");
        }
        buffer.append(padNumber(days) + "d");
        buffer.append(padNumber(hours) + "h");
        buffer.append(padNumber(minutes) + "m");
        buffer.append(padNumber(seconds) + "s");
        
        return buffer.toString();
    }
    
    /**
     * Pads a number with zero if it is below 10
     * @param number
     * @return
     */
    private static String padNumber(int number){
        if (number < 10){
            return "0" + number;
        } else {
            return number + "";
        }
    }
}

package carletti.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
/**
 *@group Lars Nielsen, Malik Lasse Lund, Martin RÀnn Bundgaard
 *@class Position
 *@author Lars Nielsen
 */
public class Position {

    @Id
    @GeneratedValue
    private int id;
    private String posID;
    @OneToOne(mappedBy = "position")
    private SubProduct sp; // Holds which product is on the position
    public Position(String posID) {
        this.posID = posID;
    }
    
    public Position()
    {
        
    }

    public String getPosID() {
        return posID;
    }

    public void setPosID(String posID) {
        this.posID = posID;
    }
    
    public SubProduct getSubProduct(){
        return sp;
    }

    
    /*
     * @precon There can't be removed a SubProduct from a position, where there
     * are non located
     */
    public boolean removeSubProductFromPosition() {
        if (sp == null) {
            return false;
        } else {
            sp = null;
            return true;
        }
    }
    
    /*
     * @precon There can't be put a SubProduct on a position where there already
     * is another SubProduct already position
     */
    public boolean putSubProductOnPosition(SubProduct sp) {
        if (this.sp == null) {
            putSubProductOnPositionUD(sp);
            sp.setPositionUD(this);
            return true;
        } else {
            return false;
        }
    }

    
    void putSubProductOnPositionUD(SubProduct sp){
        this.sp = sp;
    }
    
    public String toString(){
        return getPosID();
    }
    
    
}
package carletti.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 * Product encapsulates the name,
 * description and treatment of each sub-product.
 * 
 * @author Malik Lund
 *
 */
@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    @OneToOne(cascade={CascadeType.PERSIST})
    private Treatment treatment;
    
    /**
     * Create a Product-object. Id is generated automatically.
     * @param name A short name for the product.
     * @param description A description of the product.
     * @param treatment A treatment plan for the product.
     */
    public Product(String name, String description, Treatment treatment) {
        this.name = name;
        this.description = description;
        this.treatment = treatment;
    }
    
    /**
     * Empty constructor for JPA.
     */
    public Product()
    {
        
    }

    /**
     * Get the treatment associated with this product
     * type.
     * @return The Treatment object associated with this product type.
     */
    public Treatment getTreatment() {
        return treatment;
    }

    /**
     * Replace the treatment associated with this product
     * type.
     * @param treatment The new Treatment object to be associated with this product type.
     */
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    /**
     * Get the id given by the database.
     * @return This objects id represented by an int value.
     */
    public int getId(){
        return id;
    }
    
    /**
     * Get the name of this product.
     * @return A String object containing the name.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Replace the name of this product.
     * @param newName The new name as a String object.
     */
    public void setName(String newName){
        this.name = newName;
    }
    
    /**
     * Get a description of the product.
     * @return The description of the product as a String.
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Replace the description of this product.
     * @param newDescription A String object that is to be the new description.
     */
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
    
//  Added by Martin
    public String toString()
    {
        return name;
    }
//
}
package carletti.model;
/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin RÀnn Bundgaard
 * @author: Lars Nielsen
 * @enum-class: State
 */
public enum State { TREATMENT, DRYING, DONE, WASTE}
package carletti.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin RÀnn Bundgaard
 * @author: Lars Nielsen
 * @class: SubProduct
 */
public class SubProduct implements Comparable<SubProduct> {

    @Id
    @GeneratedValue
    private int id; // To have a key for the database
    private String name;
    @Enumerated(EnumType.STRING)
    private State state; // The current state which a SubProduct is in

    @OneToMany
    private List<SubTreatment> subtreatments = new ArrayList<SubTreatment>();
    private int currentSubTreatmentIndex = 0;
    @OneToOne(cascade = { CascadeType.PERSIST })
    private Product product;
    private long timeAdded;
    @OneToOne(cascade={CascadeType.PERSIST})
    private Position position;

    public SubProduct(String name, Product product, long timeAdded,
            Position position) {

        this.name = name;
        this.state = State.DRYING;
        this.setProduct(product);
        this.timeAdded = timeAdded;
        this.setPosition(position);
    }

    public SubProduct() {

    }

    public long getTimeAdded() {
        return timeAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<SubTreatment> getSubtreatments() {
        return new ArrayList<SubTreatment>(subtreatments);
    }

    private void setSubtreatments(List<SubTreatment> subtreatments) {
        this.subtreatments = subtreatments;
        setCurrentSubTreatmentIndex(0);
    }

    public int getCurrentSubTreatmentIndex() {
        return currentSubTreatmentIndex;
    }

    public void setCurrentSubTreatmentIndex(int currentSubTreatmentIndex) {
        this.currentSubTreatmentIndex = currentSubTreatmentIndex;
    }

    public Product getProduct() {
        return product;
    }

    private void setProduct(Product product) {
        this.product = product;
        setSubtreatments(product.getTreatment().getSubTreatments());
    }

    /*
     * @post subtreatments is in sort by need
     */
    public void nextSubTreatment() {
        if (currentSubTreatmentIndex < (subtreatments.size() - 1)) {
            setCurrentSubTreatmentIndex(getCurrentSubTreatmentIndex() + 1);
            setState(State.DRYING); // Martin
        } else if (currentSubTreatmentIndex >= (subtreatments.size() - 1)) {
            setState(State.DONE);
        }
    }

    public SubTreatment getCurrentSubTreatment() {
        return subtreatments.get(getCurrentSubTreatmentIndex());
    }

    public String getTime(long time) {
        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy '-' HH:mm:ss");
        return sd.format(time);

    }

    /**
     * @author Lars
     * @return
     */

    public Position getPosition() {
        return position;
    }

    void setPositionUD(Position position) {
        this.position = position;
    }

    public void setPosition(Position position) {
        if (position == null){
            this.position.putSubProductOnPositionUD(null);
        } else {
            position.putSubProductOnPositionUD(this);
        }
        setPositionUD(position);
    }

    /**
     * 
     * @author Martin
     */
    @Override
    public String toString() {
        return LongToStringParser.parseLongToString(timeLeft()) + " | " + getTime(timeAdded) + " | "
                + getId() + " " + name + " " + getState() + " " + (getCurrentSubTreatmentIndex() + 1)
                + "" + " / " + getSubtreatments().size();
    }

    /**
     * 
     * @author Martin
     * @return
     */
    public long timeLeft() {
        long max = (getCurrentSubTreatment().getDryMax() + timeAdded - System
                .currentTimeMillis());

        return max;
    }

    /** 
     * 
     * @author Martin
     */
    
//  @Override
//  public int compareTo(SubProduct otherSubProduct) {
//      return (int) (timeLeft() - otherSubProduct.timeLeft());
//  }
    
    @Override
    public int compareTo(SubProduct otherSupProduct)
    {
        long l = (timeLeft() - otherSupProduct.timeLeft());
    
        int result = 0;
        
        if (l < 0)
            result = -1; 
        
        else if (l > 0)
            result = 1;
     
        return result;
        
    }
}
package carletti.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin RÀnn Bundgaard
 * @author: Lars Nielsen
 * @class: SubTreatment
 */
public class SubTreatment {
    
    /*
     * Holds the drying time for minimum, prime and maximum drying time
     */
    @Id
    @GeneratedValue
    private int id; //Is need for the JPA identification 
    private long dryMin, dryPrime, dryMax;
    private String name;
    
    SubTreatment(String name, long dryMin, long dryPrime, long dryMax){
        this.setName(name);
        this.dryMin = dryMin;
        this.dryPrime = dryPrime;
        this.dryMax = dryMax;
        
    }
    
    public SubTreatment()
    {
        
    }

    public long getDryMin() {
        return dryMin;
    }

    public void setDryMin(long dryMin) {
        this.dryMin = dryMin;
    }

    public long getDryPrime() {
        return dryPrime;
    }

    public void setDryPrime(long dryPrime) {
        this.dryPrime = dryPrime;
    }

    public long getDryMax() {
        return dryMax;
    }

    public void setDryMax(long dryMax) {
        this.dryMax = dryMax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString()
    {
        return "\n" + name + "\n" + "min= " + getDryMin() + "\nprime= " + getDryPrime() + "\nmax= " + getDryMax();
    }
}
package carletti.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;



@Entity
/**
 * Treatment represents the various drying treatments a product 
 * needs to complete before it is a finished product.
 * 
 * @author Malik Lund
 *
 */
public class Treatment {
    
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private List<SubTreatment> subTreatments;
    
    /**
     * 
     * @param name
     */
    
    public Treatment()
    {
        
    }
    
    public Treatment(String name){
        this.name = name;
        subTreatments = new ArrayList<SubTreatment>();
    }
    
    /**
     * Creates and appends a new treatment to the list.
     * @param name The name of the sub-treatment.
     * @param dryMin Minimum drying time.
     * @param dryPrime Optimal or prime drying time.
     * @param dryMax Maximal drying time.
     */
    public void createSubTreatment(String name, long dryMin, long dryPrime, long dryMax){
        SubTreatment st = new SubTreatment(name, dryMin, dryPrime, dryMax);
        subTreatments.add(st);
    }
    
    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }

    /**
     * OBS: Altering the returned list has no effect on the
     *      internal list (though altering each object in the
     *      list have an effect).
     * @return A new List.
     */
    public List<SubTreatment> getSubTreatments() {
        return new ArrayList<SubTreatment>(subTreatments);
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString()
    {
        return name + "\n" + getSubTreatments().toString() + "\n";
    }
}
package carletti.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import carletti.dao.Dao;
import carletti.dao.LocalDao;
import carletti.model.LongToStringParser;
import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * 
 * @author Martin
 * 
 */

public class Service
{

    private Dao dao;
    private static Service serviceInstance;

    private Service(Dao dao)
    {
        this.dao = dao;
    }

    /**
     * 
     * @param dao
     *            The dao to use. OBS: After initial call, dao doesn't change!
     * @return The instance
     * @author Malik Lund
     */
    public static Service getInstance(Dao dao)
    {
        if (serviceInstance == null)
        {
            serviceInstance = new Service(dao);
        }
        return serviceInstance;
    }

    /**
     * Simple getInstance method that defaults to a local, impersistent storage
     * in memory. If a call has been made to getInstance(Dao) an instance with
     * the previously selected Dao is returned.
     * 
     * @return
     */
    public static Service getInstance()
    {
        if (serviceInstance == null)
        {
            serviceInstance = new Service(LocalDao.getInstance());
        }
        return serviceInstance;
    }

    /**
     * 
     * @param name
     *            The name of the sup-product.
     * @param product
     *            The product of the sub-product.
     * @param position
     *            The position of the sub-product.
     * @return Sub-product.
     */
    public SubProduct createSubProduct(String name, Product product,
            Position position)
    {
        SubProduct sp = new SubProduct(name, product,
                System.currentTimeMillis(), position); // TODO Make timestamp an
                                                        // option to the user
        dao.storeSubProduct(sp);

        return sp;
    }

    /**
     * Remove sub-product
     * 
     * @param subProduct
     *            The sub-product that is to be removed.
     */
    public void removeSubproduct(SubProduct subProduct)
    {
        dao.removeSubProduct(subProduct);
    }

    /**
     * Waste sub-product
     * 
     * @param subProduct
     *            The sub-product that is to be wasted
     */
    public void discardSubProduct(SubProduct subProduct)
    {
        State waste = State.WASTE;
        dao.changeStateOfSubProduct(subProduct, waste);
    }

    /**
     * Get sub-product
     * 
     * @param subProduct
     *            The sub-product to be returned.
     * @return Sub-product.
     */
    public SubProduct getSubproduct(SubProduct subProduct)
    {

        int i = dao.getSubProducts().indexOf(subProduct);
        if (i >= 0)
        {
            return dao.getSubProducts().get(i);
        } else
        {
            return null;
        }
    }

    /**
     * 
     * @author Malik Lund
     * @return
     */
    public List<Treatment> getTreatments()
    {
        return dao.getTreatments();
    }

    /**
     * Create treatment.
     * 
     * @param name
     *            The name of the treatment
     * @return Treatment
     */
    public Treatment createTreatment(String name)
    {
        Treatment t = new Treatment(name);
        return t;
    }

    /**
     * remove treatment
     * 
     * @param treatment
     *            The treatment to be removed.
     */
    public void removeTreatment(Treatment treatment)
    {
        dao.removeTreatment(treatment);
    }

    /**
     * Create new Product.
     * 
     * @param name
     *            The name of the product.
     * @param description
     *            A description of the product.
     * @param treatment
     *            The treatment for the product.
     * @return Product.
     */
    public Product createProduct(String name, String description,
            Treatment treatment)
    {
        Product p = new Product(name, description, treatment);
        dao.storeProduct(p);
        return p;
    }

    /**
     * Remove product
     * 
     * @param product
     *            The product to be removed.
     */
    public void removeProduct(Product product)
    {
        dao.removeProduct(product);
    }

    /**
     * Get product.
     * @param product The product to be returned
     * @return Product.
     */
    public Product getProduct(Product product)
    {
        int i = dao.getProducts().indexOf(product);
        if (i >= 0)
        {
            return dao.getProducts().get(i);
        } else
        {
            return null;
        }

    }
    
    /**
     * Next treatment.
     * @param subProduct The sub-product to be sent to the next treatment
     */
    public void nextTreatnemt(SubProduct subProduct)
    {
        if (subProduct.getCurrentSubTreatmentIndex() < (subProduct
                .getSubtreatments().size() - 1))
        {
            subProduct.setCurrentSubTreatmentIndex(subProduct
                    .getCurrentSubTreatmentIndex() + 1);
            dao.changeStateOfSubProduct(subProduct, State.DRYING); // Martin
        } else if (subProduct.getCurrentSubTreatmentIndex() >= (subProduct
                .getSubtreatments().size() - 1))
        {
            dao.changeStateOfSubProduct(subProduct, State.DONE);
        }
    }

    /**
     * Set product done.
     * @param subProduct The subproduct of which the state is to change to done.
     */
    public void subProductDone(SubProduct subProduct)
    {
        State done = State.DONE;
        dao.changeStateOfSubProduct(subProduct, done);
    }

    /**
     * Gets list of all done products.
     * @return
     */
//  public List<SubProduct> showAllDoneProduct()
//  {
//      State done = State.DONE;
//
//      return dao.getSubProducts(done);
//  }
//
    public List<Product> getProducts()
    {
        return dao.getProducts();
    }
//
//  public List<SubProduct> showAllSubProduct()
//  {
//      List<SubProduct> list = dao.getSubProducts();
//
//      Collections.sort(list);
//      return list;
//  }
//
//  public String getInfoAboutSubProduct(SubProduct subProduct)
//  {
//      int i = dao.getSubProducts().indexOf(subProduct);
//      return dao.getSubProducts().get(i) + "";
//  }
//
//  public List<SubProduct> getDryingSubProduct()
//  {
//      State drying = State.DRYING;
//      return dao.getSubProducts(drying);
//  }

    // --------- Malik-------------
    public void createSomeObjects()
    {
        Service s = Service.getInstance();

        s.createPosition("A1");
        s.createPosition("A2");
        s.createPosition("A3");
        s.createPosition("B1");
        s.createPosition("B2");
        s.createPosition("B3");
        s.createPosition("C1");
        s.createPosition("C2");
        s.createPosition("C3");

        Treatment t1 = s.createTreatment("Red chocolate MMs");
        t1.createSubTreatment("1st drying", generateTime(2), generateTime(30),
                generateTime(0, 1));
        t1.createSubTreatment("2nd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        t1.createSubTreatment("3rd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        Product p1 = s.createProduct("Red Chocolate MMs",
                "Info about red chocolate MMs", t1);

        Treatment t2 = s.createTreatment("Liquorice");
        t2.createSubTreatment("1st drying", generateTime(0, 15),
                generateTime(0, 20), generateTime(0, 25));
        t2.createSubTreatment("2nd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        Product p2 = s.createProduct("Liquorice",
                "Liquorice with coloured sugar layer", t2);

        Treatment t3 = s.createTreatment("Coffeebean");
        t3.createSubTreatment("1st drying", generateTime(0, 55),
                generateTime(0, 60), generateTime(0, 10, 1));
        t3.createSubTreatment("2nd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        t3.createSubTreatment("3rd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        Product p3 = s.createProduct("Coffee Bean",
                "Coffee paste with a layer of chocolate", t3);

        List<Position> positions = s.getPositions();

        SubProduct sp1 = s.createSubProduct("Foobar", p1, positions.get(0));
        SubProduct sp2 = s.createSubProduct("Barbaz", p2, positions.get(4));
        SubProduct sp3 = s.createSubProduct("Bazfoo", p3, positions.get(8));
    }

    /**
     * Takes a variable number of arguments and generates a time in
     * milliseconds.
     * 
     * The first value represents seconds, the second minutes, the third hours
     * and the fourth hours. Any arguments after that are ignored.
     * 
     * @author Malik Lund
     * @param times
     * @return
     */
    private long generateTime(int... times)
    {
        long time = 0;
        long[] constants =
        { 1000, 1000 * 60, 1000 * 60 * 60, 1000 * 60 * 60 * 24 };
        int roof = Math.min(times.length, 4);
        for (int i = 0; i < roof; i++)
        {
            time += times[i] * constants[i];
        }
        return time;
    }

    // ----------------------------

    
    //--------Martin---------------
    /**
     * Get a list of all sub-product with the given state. 
     * @param state The state for the product to be returned.
     * @return list of products.
     */
    public List<SubProduct> getSubProducts(State state)
    {
        ArrayList<SubProduct> subProduct = new ArrayList<SubProduct>();
        for (int i = 0; i < dao.getSubProducts().size(); i++)
        {
            if (dao.getSubProducts().get(i).getState() == state)
            {
                subProduct.add(dao.getSubProducts().get(i));
            }
        }
        Collections.sort(subProduct);
        return subProduct;
    }
    //--------------------------------

    
    // -----Lars
    public List<Position> getPositions()
    {
        return dao.getPositions();
    }

    public void createPosition(String posID)
    {
        dao.storePosition(new Position(posID));

    }

    // -----

    /**
     * 
     * @param sp
     *            The product of which the state id to change.
     * @param state
     *            The new state of the product.
     */
    public void changeState(SubProduct sp, State state)
    {
        dao.changeStateOfSubProduct(sp, state);
    }
}
package carletti.test.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * @author Malik
 *
 */
public class DaoTest {
    Dao dao = JpaDao.getInstance();

    @Before
    public void setUp() throws Exception {
        
    }
    
    @After
    /**
     * Delete everything before starting new test.
     */
    public void cleanUp(){
        for (SubProduct sp: dao.getSubProducts()){
            dao.removeSubProduct(sp);
        }
        
        for(Product p: dao.getProducts()){
            dao.removeProduct(p);
        }
        
        for (Treatment t: dao.getTreatments()){
            dao.removeTreatment(t);
        }
    }

    @Test
    public void storeTreatmentTest() {
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        dao.storeTreatment(t1);
        List<Treatment> treatments = dao.getTreatments();
        
        assertEquals("Size differs from 1", 1, treatments.size());
        
        assertEquals("Name differs", "TestTreatment", treatments.get(0).getName());
        
        Treatment retrievedTreatment = treatments.get(0);
        
        assertEquals("1st SubTreatmentDiffers", t1.getSubTreatments().get(0), retrievedTreatment.getSubTreatments().get(0));
    }
    
    @Test
    public void removeTreatmentTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        dao.storeTreatment(t1);
        dao.removeTreatment(t1);
        
        assertEquals("List of treatments not empty", 0, dao.getTreatments().size());
    }
    
    @Test
    public void storeProductTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        dao.storeTreatment(t1);
        Product p1 = new Product("Product1", "ProductDescription", t1);
        dao.storeProduct(p1);
        List<Product> products = dao.getProducts();
        
        assertEquals("Size is not 1", 1, products.size());
        
        Product retrievedProduct = dao.getProducts().get(0);
        
        assertEquals("Products differs", p1, retrievedProduct);
        
        assertEquals("Treatment differs", t1, retrievedProduct.getTreatment());
    }
    
    @Test
    public void removeProductTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        Product p1 = new Product("Product1", "ProductDescription", t1);
        dao.storeProduct(p1);
        dao.removeProduct(p1);
        
        assertEquals("Product not deleted", 0, dao.getProducts().size());
    }
    
    @Test
    public void storeSubProductTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        Product p1 = new Product("Product1", "ProductDescription", t1);
        dao.storeProduct(p1);
        SubProduct sp = new SubProduct("sp1", dao.getProducts().get(0), System.currentTimeMillis(), new Position("2A"));
        dao.storeSubProduct(sp);
        
        assertEquals("List of subproducts differs from 1", 1, dao.getProducts().size());
        
        assertEquals("Subproducts differ", sp, dao.getSubProducts().get(0));
        
        assertEquals("Product differs", p1.getName(), dao.getProducts().get(0).getName());
    }
}
/**
 * @author Lars Nielsen, Mailk Lasse Lund, Martin RÀn Bundgaard
 * @Class PositionTest
 * @programmer Lars Nielsen
 */
package carletti.test.model;

import org.junit.After;
import org.junit.Test;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.Treatment;

public class PositionTest {

    @Test
    public void testPosition() {
        //fail("Not yet implemented");
        Position p = new Position("1A");
        
    }

    @Test
    public void testGetPosID() {
        //fail("Not yet implemented");
        Position p = new Position("1A");
        p.getPosID();
    }

    @Test
    public void testSetPosID() {
//      fail("Not yet implemented");
        Position p = new Position("1A");
        p.setPosID("2A");
    }

    @Test
    public void testPutSubProductOnPosition() {
//      fail("Not yet implemented");
        Treatment tm = new Treatment("Kain");
        Product pd = new Product("name", "description", tm);
        SubProduct sp = new SubProduct("Judas", pd, 10000, new Position("1M"));
        Position p = new Position("1A");
        p.putSubProductOnPosition(sp);
    }

    @Test
    public void testRemoveSubProductFromPosition() {
//      fail("Not yet implemented");
        Treatment tm = new Treatment("Kain");
        Product pd = new Product("name", "description", tm);
        SubProduct sp = new SubProduct("Judas", pd, 10000, new Position("2M"));
        Position p = new Position("1A");
        p.putSubProductOnPosition(sp);
        p.removeSubProductFromPosition();
    }
}
/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Product;
import carletti.model.Treatment;

public class ProductTest
{

    Treatment treatment1;
    Treatment treatment2;
    Product p1;
    
    @Before
    public void setUp() throws Exception
    {
        treatment1 = new Treatment("Treatment1"); 
        
        p1 = new Product("Produkt1", "Hej dette er en test",treatment1);
    }
    
    @Test
    public void Product()
    {
        Product p2 = new Product("produkt2", "test", treatment1);
        assertEquals(02, p2.getId());
        assertEquals("produkt2", p2.getName());
        assertEquals("test", p2.getDescription());
        assertEquals(treatment1, p2.getTreatment());
    }

    @Test
    public void testGetTreatment()
    {
        assertEquals(treatment1, p1.getTreatment());
    }

    @Test
    public void testSetTreatment()
    {
        
        p1.setTreatment(treatment2);
        assertEquals(treatment2, p1.getTreatment());
    }

    @Test
    public void testGetId()
    {
        assertEquals(01, p1.getId());
    }

    @Test
    public void testGetName()
    {
        assertEquals("Produkt1", p1.getName());
    }

    @Test
    public void testSetName()
    {
        p1.setName("Produkt11");
        assertEquals("Produkt11", p1.getName());
    }

    @Test
    public void testGetDescription()
    {
        assertEquals("Hej dette er en test", p1.getDescription());
    }

    @Test
    public void testSetDescription()
    {
        p1.setDescription("Den nye");
        assertEquals("Den nye", p1.getDescription());
    }

}
/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.dao.LocalDao;
import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;
import carletti.model.State;

public class ServiceTest
{

    private Product product;
    private Treatment treatment;
    private SubProduct subproduct;
    private SubProduct subproduct2;
//  private Dao localDao = LocalDao.getInstance();
    private Dao dao = JpaDao.getInstance();
    
    private Position p1;
    private Position p2;
    
    private Service service;
    
    
    @Before
    public void setUp() throws Exception
    {
        service = Service.getInstance(dao);
        treatment = service.createTreatment("testTreatment");
        treatment.createSubTreatment("subTreatment1", 100, 200, 300);
        treatment.createSubTreatment("subTreatment2", 400, 500, 600);
        
        product = service.createProduct("product", "dette er en test", treatment );
        p1 = new Position("1");
        p2 = new Position("2");
        subproduct = service.createSubProduct("Sub", product, p1);
        subproduct2 = service.createSubProduct("Sub2", product, p2);
    }
    
    @After
    public void setAfter()
    {   
        for (SubProduct sp : dao.getSubProducts())
        {
            service.removeSubproduct(sp);
        }
        
        for (Product p : service.getProducts())
        {
            service.removeProduct(p);
        }
            
                
        for (Treatment t : dao.getTreatments())
        {
            service.removeTreatment(t);
        }
        
        

    }

    @Test
    public void testCreateSubProduct()
    {
        SubProduct subProduct = service.createSubProduct("test", product, p1);
        assertEquals("test", service.getSubproduct(subProduct).getName());
        assertEquals(product, service.getSubproduct(subProduct).getProduct());
        assertEquals(p1, service.getSubproduct(subProduct).getPosition());
    }

    @Test
    public void testRemoveSubproduct()
    {
        service.removeSubproduct(subproduct);
        
        assertEquals(null, service.getSubproduct(subproduct));
    }

    @Test
    public void testDiscardSubProduct()
    {
        service.discardSubProduct(subproduct);
        assertEquals(State.WASTE , service.getSubproduct(subproduct).getState());
    }

    @Test
    public void testGetSubproduct()
    {
        assertEquals("Sub2", service.getSubproduct(subproduct2).getName());
        assertEquals(product, service.getSubproduct(subproduct2).getProduct());
    }

    @Test
    public void testCreateTreatment()
    {
        Treatment treatment = service.createTreatment("treatment");
        Product p = service.createProduct("name", "description", treatment);
        
        assertEquals(treatment, service.getProduct(p).getTreatment());
    }

    @Test
    public void testCreateProduct()
    {
        Product product = service.createProduct("name", "description", treatment);
        assertEquals(product, service.getProduct(product));
    }

    @Test
    public void testRemoveProduct()
    {
        Treatment t = service.createTreatment("sadf"); 
        Product product = service.createProduct("asdfdsa", "dsafdsaf", t);
        service.removeProduct(product);
        assertEquals(null, service.getProduct(product));
    }

    @Test
    public void testGetProduct()
    {
        assertEquals(product, service.getProduct(product));
    }

    @Test
    public void testNextTreatnemt()
    {
        assertEquals("subTreatment1", service.getSubproduct(subproduct).getCurrentSubTreatment().getName());
        service.nextTreatnemt(subproduct);
        assertEquals("subTreatment2", service.getSubproduct(subproduct).getCurrentSubTreatment().getName());
    }

    @Test
    public void testSubProductDone()
    {
        service.changeState(subproduct, State.DONE);
        assertEquals(State.DONE, service.getSubproduct(subproduct).getState());
    }

    @Test
    public void testGetProducts()
    {
        assertEquals(1, service.getProducts().size());  
    }

    @Test
    public void testGetSubProducts()
    {
        subproduct.setState(State.DONE);
        subproduct2.setState(State.WASTE);
        assertEquals(1, service.getSubProducts(State.DONE).size());
        assertEquals(1, service.getSubProducts(State.WASTE).size());
    }
    
}
package carletti.test.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;
import carletti.dao.JpaDao;

/**
 * 
 * @author Malik Lund
 *
 */
public class SubProductTest {
    Service service = Service.getInstance(JpaDao.getInstance());
    SubProduct subproduct;
    Treatment treatment;
    Product product;
    Position position;
    long timeAdded;

    @Before
    public void setUp() throws Exception {
        treatment = service.createTreatment("Treatment");
        treatment.createSubTreatment("Subtreatment 1", 10, 20, 30);
        treatment.createSubTreatment("Subtreatment 2", 20, 30, 40);
        treatment.createSubTreatment("Subtreatment 3", 30, 40, 50);
        product = service.createProduct("TestProduct", "Product to test", treatment);
        timeAdded = System.currentTimeMillis();
        position = new Position("A2");
        subproduct = new SubProduct("Name", product, timeAdded, position);
    }

    /**
     * Test getters.
     */
    @Test
    public void testSubProduct() {
        assertEquals("Time differs", timeAdded, subproduct.getTimeAdded());
        assertEquals("Name differs", "Name", subproduct.getName());
        assertEquals("State differs", State.DRYING, subproduct.getState());
        
        List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
        List<SubTreatment> expectedSubTreatments = treatment.getSubTreatments();
        assertEquals("Differing number of subtreatments", expectedSubTreatments.size(), actualSubTreatments.size());
        for (int i = 0; i < expectedSubTreatments.size(); i++){
            assertEquals("Differing SubTreatments", expectedSubTreatments.get(i), actualSubTreatments.get(i));
        }
        assertEquals("Current subtreatment differs", 0, subproduct.getCurrentSubTreatmentIndex());
        assertEquals("Product differs", product, subproduct.getProduct());
        assertEquals("Different subtreatments", treatment.getSubTreatments().get(0), subproduct.getCurrentSubTreatment());
    }
    
    /**
     * Test setId
     */
    @Test
    public void testSetId(){
        subproduct.setId(1337);
        assertEquals("Id not changed", 1337, subproduct.getId());
    }
    
    /**
     * Test setName
     */
    @Test
    public void testSetName(){
        subproduct.setName("NewName");
        assertEquals("Name not changed", "NewName", subproduct.getName());
    }
    
    /**
     * Test setState
     */
    @Test
    public void testSetState(){
        subproduct.setState(State.WASTE);
        assertEquals("State not changed", State.WASTE, subproduct.getState());
    }
    
    /**
     * Test setSubtreatments
     */
//  @Test
//  public void testSetSubtreatments(){
//      subproduct.nextSubTreatment();
//      subproduct.nextSubTreatment();
//      
//      Treatment newTreatment = service.createTreatment("NewTreatment");
//      newTreatment.createSubTreatment("NewSubTreatment", 50, 60, 70);
//      subproduct.setSubtreatments(newTreatment.getSubTreatments());
//      List<SubTreatment> expectedSubTreatments = newTreatment.getSubTreatments();
//      List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
//      for (int i = 0; i < expectedSubTreatments.size(); i++){
//          assertEquals("Subproducts differs", expectedSubTreatments.get(i), actualSubTreatments.get(i));
//      }
//      
//      subproduct.getCurrentSubTreatment();
//  }
    
//  /**
//   * Test addition of subTreatment
//   */
//  @Test
//  public void testAddSubTreatment(){
//      SubTreatment st = new SubTreatment("AddedTreatment", 10, 12, 14);
//      subproduct.addSubTreatment(st);
//      assertEquals("List not enlarged", 4, subproduct.getSubtreatments().size());
//      assertEquals("Last element not the recently added", st, subproduct.getSubtreatments().get(3));
//  }
    
    /**
     * Test product type change
     */
//  @Test
//  public void testSetProduct(){
//      subproduct.nextSubTreatment();
//      subproduct.nextSubTreatment();
//      subproduct.nextSubTreatment();
//      
//      Treatment newTreatment = service.createTreatment("NewTreatment");
//      newTreatment.createSubTreatment("T1", 1, 2, 3);
//      newTreatment.createSubTreatment("T2", 2, 3, 4);
//      Product newProduct = service.createProduct("NewProduct", "Description of new product", newTreatment);
//      subproduct.setProduct(newProduct);
//      assertEquals("Product differs", newProduct, subproduct.getProduct());
//
//      List<SubTreatment> expectedSubTreatments = newTreatment.getSubTreatments();
//      List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
//      for (int i = 0; i < expectedSubTreatments.size(); i++){
//          assertEquals("Subproducts differs", expectedSubTreatments.get(i), actualSubTreatments.get(i));
//      }
//      
//      subproduct.getCurrentSubTreatment();
//  }
    
    /**
     * Test nextSubTreatment()
     */
    @Test
    public void testNextSubTreatment(){
        subproduct.nextSubTreatment();
        assertEquals("Iteration error", treatment.getSubTreatments().get(1), subproduct.getCurrentSubTreatment());
        
        subproduct.nextSubTreatment();
        subproduct.nextSubTreatment();
        subproduct.nextSubTreatment();
        assertEquals("Iterated too far", treatment.getSubTreatments().get(2), subproduct.getCurrentSubTreatment());
    }
}
/**
 * @author Martin
 */


package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.SubTreatment;
import carletti.model.Treatment;

public class SubTreatmentTest
{
    private Treatment t;
    private SubTreatment st;

    @Before
    public void setUp() throws Exception
    {
        t = new Treatment("Treatment");
        t.createSubTreatment("subtreatment1", 10, 20, 30);
        st = t.getSubTreatments().get(0);
    }

    @Test
    public void testSubTreatment()
    {
        t = new Treatment("Treatment");
        t.createSubTreatment("Proeve", 40, 50, 60);
        SubTreatment s = t.getSubTreatments().get(0);
        assertEquals("Proeve", s.getName());
        assertEquals(40, s.getDryMin());
        assertEquals(50, s.getDryPrime());
        assertEquals(60, s.getDryMax());
    }

    @Test
    public void testGetDryMin()
    {
        assertEquals(10, st.getDryMin());
        
    }

    @Test
    public void testSetDryMin()
    {
        st.setDryMin(15);
        assertEquals(15, st.getDryMin());
    }

    @Test
    public void testGetDryPrime()
    {
        assertEquals(20, st.getDryPrime());
    }

    @Test
    public void testSetDryPrime()
    {
        st.setDryPrime(25);
        assertEquals(25, st.getDryPrime());
    }

    @Test
    public void testGetDryMax()
    {
        assertEquals(30, st.getDryMax());
    }

    @Test
    public void testSetDryMax()
    {
        st.setDryMax(65);
        assertEquals(65, st.getDryMax());
    }

    @Test
    public void testGetName()
    {
        assertEquals("subtreatment1", st.getName());
    }

    @Test
    public void testSetName()
    {
        st.setName("newName");
        assertEquals("newName", st.getName());
    }

    @Test
    public void testToString()
    {
        String s = "\n" + "subtreatment1" + "\n" + "min= " + "10" + "\nprime= " + "20" + "\nmax= " + "30";
        assertEquals(s, st.toString());
    }
}
/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Treatment;

public class TreatmentTest
{
    
    private Treatment t1;           

    @Before
    public void setUp() throws Exception
    {
        t1 = new Treatment("treatment1");
    }

    @Test
    public void testTreatment()
    {
        Treatment t2 = new Treatment("treatment2");
        assertEquals("treatment2", t2.getName());
    }

    @Test
    public void testCreateSubTreatment()
    {
        
        t1.createSubTreatment("name", 10, 20, 30);
        assertEquals("name", t1.getSubTreatments().get(0).getName());
        assertEquals(10, t1.getSubTreatments().get(0).getDryMin());
        assertEquals(20, t1.getSubTreatments().get(0).getDryPrime());
        assertEquals(30, t1.getSubTreatments().get(0).getDryMax());
    }

    @Test
    public void testGetName()
    {
        assertEquals("treatment1", t1.getName());
    }

    @Test
    public void testGetSubTreatments()
    {
        t1.createSubTreatment("name", 10, 20, 30);
        assertEquals("name", t1.getSubTreatments().get(0).getName());
        assertEquals(10, t1.getSubTreatments().get(0).getDryMin());
        assertEquals(20, t1.getSubTreatments().get(0).getDryPrime());
        assertEquals(30, t1.getSubTreatments().get(0).getDryMax());
    }

    @Test
    public void testSetName()
    {
        t1.setName("newName");
        assertEquals("newName", t1.getName());
    }

}
package carletti;

public class App {
    public static void main(String[] args){
        LoadGui lg = new LoadGui();
    }
}
/** @author Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 * 
 */
package carletti;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.dao.LocalDao;
import carletti.gui.MainFrame;
import carletti.service.Service;

@SuppressWarnings("serial")
public class LoadGui extends JFrame {

    private Dimension frameSize = new Dimension(300, 200);
    private JPanel selectPanel;

    // private int x = 20, y = 80;

    public LoadGui() {

        this.setTitle("Carletti - Load Screen");
        this.setSize(frameSize);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        selectPanel = new SelectorPanel();
        this.add(selectPanel);

        this.setVisible(true);

    }

    private class SelectorPanel extends JPanel implements ActionListener {
        private JButton btnOk;
        private JRadioButton rbtnLocal, rbtnDao;

        public SelectorPanel() {
            super();
            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            rbtnLocal = new JRadioButton("Local");
            rbtnDao = new JRadioButton("Database");
            btnOk = new JButton("Ok");

            rbtnLocal.addActionListener(this);
            rbtnDao.addActionListener(this);
            btnOk.addActionListener(this);

            layout.setHorizontalGroup(layout
                    .createSequentialGroup()
                    .addGroup(
                            layout.createParallelGroup(
                                    GroupLayout.Alignment.CENTER)
                                    .addComponent(rbtnLocal)
                                    .addComponent(btnOk)).addComponent(rbtnDao));

            layout.setVerticalGroup(layout
                    .createSequentialGroup()
                    .addGroup(
                            layout.createParallelGroup(
                                    GroupLayout.Alignment.CENTER)
                                    .addComponent(rbtnLocal)
                                    .addComponent(rbtnDao))
                    .addComponent(btnOk, 20, 20, 20));
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            Dao dao;
            if (ae.getSource().equals(btnOk)) {
                if (rbtnLocal.isSelected() == true
                        && rbtnDao.isSelected() == false) {
                    dao = LocalDao.getInstance();
                    startProgram(dao);
                } else if (rbtnLocal.isSelected() == false
                        && rbtnDao.isSelected() == true) {
                    dao = JpaDao.getInstance();
                    startProgram(dao);
                } 
            }
            else if(ae.getSource().equals(rbtnLocal)){
                rbtnDao.setSelected(false);
            }
            else if(ae.getSource().equals(rbtnDao)){
                rbtnLocal.setSelected(false);
            }
        }
    }
    
    private void startProgram(Dao dao){
        Service s = Service.getInstance(dao);
        s.createSomeObjects();
        this.setVisible(false);
        MainFrame mf = new MainFrame();
    }

}

---- /Users/tools/Desktop/samlet.java

package carletti.dao.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RecreateDatabase
{
    public static void main(String[] args) {
        try {
            //connection to MySQL
        	Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://10.37.129.3/master", "sa",
					"lnc20020"); // "jdbc:jtds:sqlserver://10.37.129.3:1433/Cars"
			
            
            Statement stmt = myConnection.createStatement();
            stmt.executeUpdate("DROP DATABASE Carletti");
            stmt.executeUpdate("CREATE DATABASE Carletti");
            
            System.out.println("Database recreated!");
        }
        catch (Exception e) {
            System.out.println("Error connecting to database:  "
                    + e.getMessage());
        }
    }
}
package carletti.dao;

import java.util.List;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * @author Malik Lund
 *
 */
public interface Dao {

    public List<SubProduct> getSubProducts(State state);
    public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state);
    public void storeTreatment(Treatment t);
    public void removeTreatment(Treatment t);
    public void storeProduct(Product p);
    public void removeProduct(Product p);
    public void storeSubProduct(SubProduct sp);
    public void removeSubProduct(SubProduct sp);
    public void storePosition(Position p);
    public void removePosition(Position p);
    public List<Treatment> getTreatments();
    public List<Product> getProducts();
    public List<SubProduct> getSubProducts();
    public List<Position> getPositions();
}
/**
 * @author Lars Nielse, Malik L. Lund, Matrin R. Bungaard
 * @class JpaDao
 * @programmer Lars Nielsen
 */
package carletti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

public class JpaDao implements Dao{

    private static JpaDao uniqueInstance;
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    private JpaDao(){
        emf = Persistence.createEntityManagerFactory("Carletti");
        em = emf.createEntityManager();
    }
    
    /**
     * insures that we are only able to create on JpaDao instance
     * and it is done by implementing the Singelton design pattern
     * @return
     */
    public static JpaDao getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new JpaDao();
        }
        
        return uniqueInstance;
    }
    
    @Override
    /**
     * @author Malik Lund
     */
    public void storeSubProduct(SubProduct sp){
        em.getTransaction().begin();
        em.persist(sp);
        em.getTransaction().commit();
    }
    
    public void close(){
        em.close();
        emf.close();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<SubProduct> getSubProducts(State state) {
        Query query = em.createQuery("SELECT sp FROM SubProduct sp WHERE sp.state = :state");
        query.setParameter("state", state);
        return query.getResultList();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state) {
        em.getTransaction().begin();
        subProduct.setState(state);
        em.getTransaction().commit();
        return subProduct;
    }
    @Override
    /**
     * @author Malik Lund
     */
    public void storeProduct(Product p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void removeProduct(Product p) {
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void removeSubProduct(SubProduct sp) {
        em.getTransaction().begin();
        em.remove(sp);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<Product> getProducts() {
        Query query = em.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<SubProduct> getSubProducts() {
        Query query = em.createQuery("SELECT sp FROM SubProduct sp", SubProduct.class);
        return query.getResultList();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void storeTreatment(Treatment t) {
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public void removeTreatment(Treatment t) {
        em.getTransaction().begin();
        em.remove(t);
        em.getTransaction().commit();
    }

    @Override
    /**
     * @author Malik Lund
     */
    public List<Treatment> getTreatments() {
        Query query = em.createQuery("SELECT t FROM Treatment t", Treatment.class);
        return query.getResultList();
    }
    
    //-----Lars
    @Override
    public List<Position> getPositions(){
        Query query = em.createQuery("SELECT p FROM Position p", Position.class);
        return query.getResultList();
    }

    @Override
    public void storePosition(Position p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        
        
    }

    @Override
    public void removePosition(Position p) {
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        
    }
    //-----
}
package carletti.dao;

import java.util.ArrayList;
import java.util.List;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * This class maintains connection, and any operations, with the database.
 * 
 * It is implemented as a Singleton-object to ensure there is only one
 * object interacting with the database.
 * To get an instance call the static method Dao.getInstance().
 * 
 * TODO Change implementation to use a database rather than lists.
 * 
 * @author Malik Lund
 *
 */
public class LocalDao implements Dao {
    private static LocalDao dao = null;
    private ArrayList<Treatment> treatments;
    private ArrayList<Product> products;
    private ArrayList<SubProduct> subproducts;
    private ArrayList<Position> positions;
    private int subProductnextID = 1;
    private int productNextID = 1;
    /**
     * Simply creates a Dao-object and initializes all
     * lists.
     */
    private LocalDao(){
        treatments = new ArrayList<Treatment>();
        products = new ArrayList<Product>();
        subproducts = new ArrayList<SubProduct>();      
        positions = new ArrayList<Position>();
    }
    
    /**
     * Creates a new instance of the Dao class on the first call
     * and merely returns this for any subsequent calls. 
     * @return An instance of Dao.
     */
    public static Dao getInstance(){
        if (dao == null){
            dao = new LocalDao();
        }
        return dao;
    }
    
    /**
     * Returns a list of SubProducts in the given state.
     * @param state The state of the returned SubProducts. 
     * @return A list of SubProducts in a given state. If state is null
     *         all SubProduct objects are returned.
     */
    public List<SubProduct> getSubProducts(State state){
        ArrayList<SubProduct> selectedSubproducts = new ArrayList<SubProduct>();
        for (SubProduct sp: subproducts){
            if (sp.getState().equals(state) || state == null){
                selectedSubproducts.add(sp);
            }
        }
        return selectedSubproducts;
    }
    
    /**
     * Changes the state of a SubProduct-object and saves it in the
     * database.
     * @param subProduct The object to change the state of.
     * @param state The state to change to.
     * @return The object with its new state.
     */
    public SubProduct changeStateOfSubProduct(SubProduct subProduct, State state){
        subProduct.setState(state);
        return subProduct;
    }
    
    /**
     * Store a Product object in the database.
     * @param p The Product object to store.
     */
    public void storeProduct(Product p){
        Treatment t = p.getTreatment();
        if (!treatments.contains(t)){
            storeTreatment(t);
        }
        products.add(p);
    }
    
    /**
     * Delete a product type from the system.
     * @param p The Product object to delete.
     */
    public void removeProduct(Product p){
        products.remove(p);
    }

    /**
     * Store a SubProduct object in the database.
     * @param sp The SubProduct object to store.
     */
    public void storeSubProduct(SubProduct sp){
        subproducts.add(sp);
        
    }
    
    /**
     * Delete a sub-product from the system.
     * @param sp The SubProduct object to delete.
     */
    public void removeSubProduct(SubProduct sp){
        subproducts.remove(sp);
    }
    
    /**
     * Get a list of all product types.
     * @return A list of Product objects.
     */
    public List<Product> getProducts(){
        return new ArrayList<Product>(products);
    }
    
    /**
     * Get a list of all sub-product types.
     * @return A list of SubProduct objects.
     */
    public List<SubProduct> getSubProducts(){
        return new ArrayList<SubProduct>(subproducts);
    }
    
    public int getSubProducNextID(){
        return subProductnextID;
    }
    
    public void countSubProducID(){
        subProductnextID++;
    }
    
    public int getProducNextID(){
        return productNextID;
    }
    
    public void countProducID(){
        productNextID++;
    }

    @Override
    public void storeTreatment(Treatment t) {
        treatments.add(t);
    }

    @Override
    public void removeTreatment(Treatment t) {
        treatments.remove(t);
    }

    @Override
    public List<Treatment> getTreatments() {
        return new ArrayList<Treatment>(treatments);
    }
    
    //-----Lars
    @Override
    public List<Position> getPositions() {
        return new ArrayList<Position>(positions);
    }

    
    @Override
    public void storePosition(Position p) {
        positions.add(p);
        
    }

    @Override
    public void removePosition(Position p) {
        positions.remove(p);
        
    }
    //-----
    
    
}
package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;

/**
 * The CreateNewProductDialog class handles the creation
 * of a new product and it's associated treatment plan.
 * @author Malik Lund
 *
 */
public class CreateNewProductDialog extends JDialog {
    private Service service;
    private Controller controller;
    
    private boolean newTreatment;
    
    private JPanel mainPanel, productAndSubproductPanel, productInfoPanel,
                   productButtonsPanel, treatmentNamePanel, subTreatmentPanel, subTreatmentButtonsPanel;
    private JLabel lblNewProduct, lblName, lblDescription, lblTreatmentName, lblSubTreatment;
    private JTextField txfName, txfTreatmentName;
    private JTextArea txtAreaDescription;
    private JButton btnCreate, btnCancel, btnAddSubTreatment, btnSelectTreatment;
    private JTable subTreatmentsTable;
    private NewProductSubTreatmentsTableModel subTreatmentsTableModel;
    private JScrollPane productDescriptionScrollPane, subTreatmentsScrollPane;

    /**
     * 
     */
    public CreateNewProductDialog(){
        service = service.getInstance();
        this.setTitle("Create new product");
        this.setModal(true);

        controller = new Controller();
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);
        
        productAndSubproductPanel = new JPanel();
        GroupLayout outerGroupLayout = new GroupLayout(productAndSubproductPanel);
        productAndSubproductPanel.setLayout(outerGroupLayout);
        
        outerGroupLayout.setAutoCreateGaps(true);
        outerGroupLayout.setAutoCreateContainerGaps(true);
        
        productInfoPanel = new JPanel();
        productButtonsPanel = new JPanel();
        subTreatmentPanel = new JPanel();
        subTreatmentButtonsPanel = new JPanel();
        lblNewProduct = new JLabel("New Product");
        
        mainPanel.add(lblNewProduct, BorderLayout.NORTH);
        
        //------------
        // General grouping of product information and subtreatment information
        
        outerGroupLayout.setHorizontalGroup(
            outerGroupLayout.createSequentialGroup()
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(productInfoPanel)
                    .addComponent(productButtonsPanel))
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(subTreatmentPanel)
                    .addComponent(subTreatmentButtonsPanel))
        );
        
        outerGroupLayout.setVerticalGroup(
            outerGroupLayout.createSequentialGroup()
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(productInfoPanel)
                    .addComponent(subTreatmentPanel))
                .addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(productButtonsPanel)
                    .addComponent(subTreatmentButtonsPanel))
        );
        
        mainPanel.add(productAndSubproductPanel);
        
        //------------
        // Product name and description
        
        GroupLayout productGroupLayout = new GroupLayout(productInfoPanel);
        productInfoPanel.setLayout(productGroupLayout);
        
        productGroupLayout.setAutoCreateGaps(true);
        productGroupLayout.setAutoCreateContainerGaps(true);
        
        lblName = new JLabel("Name:");
        lblDescription = new JLabel("Description:");
        txfName = new JTextField();
        txtAreaDescription = new JTextArea();
        txtAreaDescription.setLineWrap(true);
        productDescriptionScrollPane = new JScrollPane(txtAreaDescription);
        
        productGroupLayout.setHorizontalGroup(
            productGroupLayout.createSequentialGroup()
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(lblDescription))
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txfName, 150, 175, 200)
                    .addComponent(productDescriptionScrollPane, 250, 250, Short.MAX_VALUE))
        );
        
        productGroupLayout.setVerticalGroup(
            productGroupLayout.createSequentialGroup()
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txfName))
                .addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescription)
                    .addComponent(productDescriptionScrollPane, 100, 100, Short.MAX_VALUE))
        );
        
        //--------------
        // Product create and cancel buttons
        
        GroupLayout productButtonsGroupLayout = new GroupLayout(productButtonsPanel);
        productButtonsPanel.setLayout(productButtonsGroupLayout);
        
        productButtonsGroupLayout.setAutoCreateGaps(true);
        productButtonsGroupLayout.setAutoCreateContainerGaps(true);
        
        btnCreate = new JButton("Create");
        btnCancel = new JButton("Cancel");
        
        btnCreate.addActionListener(controller);
        btnCancel.addActionListener(controller);
        
        productButtonsGroupLayout.setHorizontalGroup(
            productButtonsGroupLayout.createSequentialGroup()
                .addComponent(btnCreate)
                .addComponent(btnCancel)
        );
        
        productButtonsGroupLayout.setVerticalGroup(
            productButtonsGroupLayout.createSequentialGroup()
                .addGroup(productButtonsGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnCancel))
        );
        
        //-------------
        // Subtreatments table
        
        GroupLayout subTreatmentsGroupLayout = new GroupLayout(subTreatmentPanel);
        subTreatmentPanel.setLayout(subTreatmentsGroupLayout);
        
        subTreatmentsGroupLayout.setAutoCreateGaps(true);
        subTreatmentsGroupLayout.setAutoCreateContainerGaps(true);
        
        lblSubTreatment = new JLabel("Treatments");
        treatmentNamePanel = new JPanel();
        lblTreatmentName = new JLabel("Name:    ");
        txfTreatmentName = new JTextField();
        subTreatmentsTableModel = new NewProductSubTreatmentsTableModel();
        subTreatmentsTable = new JTable(subTreatmentsTableModel);
        subTreatmentsScrollPane = new JScrollPane(subTreatmentsTable);
        
        treatmentNamePanel.setLayout(new BoxLayout(treatmentNamePanel, BoxLayout.X_AXIS));
        treatmentNamePanel.add(lblTreatmentName);
        treatmentNamePanel.add(txfTreatmentName);
        txfTreatmentName.setEnabled(false);
        subTreatmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        subTreatmentsGroupLayout.setHorizontalGroup(
            subTreatmentsGroupLayout.createSequentialGroup()
                .addGroup(subTreatmentsGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblSubTreatment)
                    .addComponent(treatmentNamePanel)
                    .addComponent(subTreatmentsScrollPane, 400, 400, 400))
        );
        
        subTreatmentsGroupLayout.setVerticalGroup(
            subTreatmentsGroupLayout.createSequentialGroup()
                .addComponent(lblSubTreatment)
                .addComponent(treatmentNamePanel)
                .addComponent(subTreatmentsScrollPane, 100, 100, Short.MAX_VALUE)
        );
        
        //-----------
        // Treatment buttons.
        
        GroupLayout subTreatmentButtonsGroupLayout = new GroupLayout(subTreatmentButtonsPanel);
        subTreatmentButtonsPanel.setLayout(subTreatmentButtonsGroupLayout);
        
        subTreatmentButtonsGroupLayout.setAutoCreateGaps(true);
        subTreatmentButtonsGroupLayout.setAutoCreateContainerGaps(true);
        
        btnAddSubTreatment = new JButton("New Treatment");
        btnSelectTreatment = new JButton("Select existing treatment");
        
        btnAddSubTreatment.addActionListener(controller);
        btnSelectTreatment.addActionListener(controller);
        
        subTreatmentButtonsGroupLayout.setHorizontalGroup(
            subTreatmentButtonsGroupLayout
                .createSequentialGroup()
                    .addComponent(btnAddSubTreatment)
                    .addComponent(btnSelectTreatment)
        );
        
        subTreatmentButtonsGroupLayout.setVerticalGroup(
            subTreatmentButtonsGroupLayout
                .createParallelGroup()
                    .addComponent(btnAddSubTreatment)
                    .addComponent(btnSelectTreatment)
        );
        
        this.pack();
    }
    
    /**
     * Handles all input for this class.
     * @author Malik Lund
     *
     */
    private class Controller implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            /**
             * Create product button.
             */
            if (ae.getSource() == btnCreate){
                boolean success = true;
                
                String subProductName = txfName.getText();
                if (subProductName.length() <= 0){
                    JOptionPane.showMessageDialog(null, "Subproduct name is empty!");
                    success = false;
                }
                
                String treatmentName = txfTreatmentName.getText();
                if (success && treatmentName.length() <= 0){
                    JOptionPane.showMessageDialog(null, "Treatment name is empty!");
                    success = false;
                }
                
                List<SubTreatment> data = subTreatmentsTableModel.getData().getSubTreatments();
                if (success && data.size() <= 0){
                    JOptionPane.showMessageDialog(null, "Treatment has no subtreatments!");
                    success = false;
                }
                
                if (success){
                    Treatment treatment = service.createTreatment(txfTreatmentName.getText());
                    for (int i = 0; i < data.size(); i++){
                        String name = data.get(i).getName();
                        long min = data.get(i).getDryMin();
                        long optimal = data.get(i).getDryPrime();
                        long max = data.get(i).getDryMax();
                        treatment.createSubTreatment(name, min, optimal, max);
                    }
                    service.createProduct(txfName.getText(), txtAreaDescription.getText(), treatment);
                    CreateNewProductDialog.this.setVisible(false);
                }
            }
            /**
             * Cancel creation of product button.
             */
            else if (ae.getSource() == btnCancel){
                CreateNewProductDialog.this.setVisible(false);
            }
            
            /**
             * New/Add subtreatment button.
             */
            else if (ae.getSource() == btnAddSubTreatment){
                if (newTreatment){
                    CreateNewSubTreatmentDialog createSubTreatmentDialog = new CreateNewSubTreatmentDialog(subTreatmentsTableModel);
                    createSubTreatmentDialog.setLocationRelativeTo(CreateNewProductDialog.this);
                    createSubTreatmentDialog.setVisible(true);
                } else {
                    newTreatment = true;
                    txfTreatmentName.setEnabled(true);
                    txfTreatmentName.setText("");
                    btnAddSubTreatment.setText("Add subtreatment");
                    subTreatmentsTableModel.setData(new Treatment("Temp"));
                }
            }
            /**
             * Select treatment button. 
             */
            else if (ae.getSource() == btnSelectTreatment){
                // save new Treatment in case you regret 
                // selecting an existing one
                Treatment temporaryTreatment = null;
                if (newTreatment){
                    temporaryTreatment = subTreatmentsTableModel.getData();
                    temporaryTreatment.setName(txfTreatmentName.getText());
                }
                
                // prepare table and tablemodel
                subTreatmentsTableModel.setData(new Treatment("Temp"));
                subTreatmentsTableModel.removeTableModelListener(subTreatmentsTable); // "disconnect" table 
                txfTreatmentName.setText("");
                
                // start selection
                SelectTreatmentDialog selectTreatmentDialog = new SelectTreatmentDialog(subTreatmentsTableModel);
                selectTreatmentDialog.setLocationRelativeTo(CreateNewProductDialog.this);
                selectTreatmentDialog.setVisible(true);
                
                // handle selection
                Treatment treatment = subTreatmentsTableModel.getData();
                if (!treatment.getName().equals("Temp")){
                    // selected an existing treatment
                    txfTreatmentName.setText(treatment.getName());
                    txfTreatmentName.setEnabled(false);
                    btnAddSubTreatment.setText("New subtreatment");
                    newTreatment = false;
                } else {
                    // didn't select an existing treatment
                    if (newTreatment){
                        // revert to previous data.
                        subTreatmentsTableModel.setData(temporaryTreatment);
                        txfTreatmentName.setText(temporaryTreatment.getName());
                    } else {
                        // no previsous data.
                        subTreatmentsTableModel.setData(new Treatment("Temp"));
                    }
                }
                
                subTreatmentsTableModel.addTableModelListener(subTreatmentsTable); // "reconnect" table
                subTreatmentsTableModel.fireTableDataChanged();

            }
        }
    }
}
package carletti.gui.dialogs;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Handles the creation of a new SubTreatment and
 * associates it with creation of a new Treatment.
 * @author Malik Lund
 *
 */
public class CreateNewSubTreatmentDialog extends JDialog {

    private Controller controller;
    private JPanel mainPanel, infoPanel;
    private TimeSelectorPanel minPanel, optimalPanel, maxPanel;
    private JLabel lblNew, lblName, lblMin, lblOptimal, lblMax;
    private JTextField txfName;
    private JButton btnCreate, btnCancel;

    private NewProductSubTreatmentsTableModel subTreatmentsTableModel;
    
    /**
     * 
     * @param subTreatmentsTableModel
     */
    public CreateNewSubTreatmentDialog(NewProductSubTreatmentsTableModel subTreatmentsTableModel){
        this.getContentPane().setLayout(new FlowLayout());
        this.setModal(true);
        this.setResizable(false);

        
        this.subTreatmentsTableModel = subTreatmentsTableModel;
        controller = new Controller();
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel);
        
        infoPanel = new JPanel();
        GroupLayout layout = new GroupLayout(infoPanel);
        infoPanel.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        minPanel = new TimeSelectorPanel();
        optimalPanel = new TimeSelectorPanel();
        maxPanel = new TimeSelectorPanel();
        lblNew = new JLabel("New subtreatment");
        lblName = new JLabel("Name:");
        lblMin = new JLabel("Minimum:");
        lblOptimal = new JLabel("Optimal:");
        lblMax = new JLabel("Maximum:");
        txfName = new JTextField();
        btnCreate = new JButton("Create");
        btnCancel = new JButton("Cancel");
        
        btnCreate.addActionListener(controller);
        btnCancel.addActionListener(controller);
        
        mainPanel.add(lblNew, BorderLayout.NORTH);
        
        /**
         * Define main grouping horizontally.
         */
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(lblMin)
                    .addComponent(lblOptimal)
                    .addComponent(lblMax)
                    .addComponent(btnCreate))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txfName)
                    .addComponent(minPanel)
                    .addComponent(optimalPanel)
                    .addComponent(maxPanel)
                    .addComponent(btnCancel))
        );
        
        /**
         * Define main grouping vertically.
         */
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txfName))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMin)
                    .addComponent(minPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOptimal)
                    .addComponent(optimalPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMax)
                    .addComponent(maxPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnCancel))
        );

        mainPanel.add(infoPanel);
        
        pack();
    }
    
    /**
     * This private class handles all input events.
     * @author Malik
     *
     */
    private class Controller implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent ae) {
            /**
             * Create button.
             */
            if (ae.getSource() == btnCreate){
                // --- input verification start ---
                boolean error = false;
                String name = txfName.getText();
                if (name.length() <= 0){
                    JOptionPane.showMessageDialog(null, "Name is empty!");
                    error = true;
                }
                
                long min = minPanel.getTime();
                if (!error && min < 0){
                    JOptionPane.showMessageDialog(null, "Minimum must be larger than 0!");
                    error = true;
                }
                
                long optimal = optimalPanel.getTime();
                if (!error && optimal < min){
                    JOptionPane.showMessageDialog(null, "Optimal must be larger than minimum!");
                    error = true;
                }
                
                long max = maxPanel.getTime();
                if (!error && max < optimal){
                    JOptionPane.showMessageDialog(null, "Maximum must be larger than optimal!");
                    error = true;
                }
                // --- input verification end ---
                
                if (!error){
                    subTreatmentsTableModel.newSubTreatment(name, min, optimal, max);
                    CreateNewSubTreatmentDialog.this.setVisible(false);
                }
            }
            /**
             * Cancel button.
             */
            else if (ae.getSource() == btnCancel){
                CreateNewSubTreatmentDialog.this.setVisible(false);
            }
        }
    }
    
    /**
     * Creates a panel containing four SelectorPanel objects (see below). One for
     * selecting number of days, one for number of hours, one for minutes
     * and one for seconds (it is assumed that it is unnecessary to input
     * milliseconds as values).
     * @author Malik Lund
     *
     */
    class TimeSelectorPanel extends JPanel{
        
        private JLabel lblDays, lblHours, lblMinutes, lblSeconds;
        private SelectorPanel daySelector, hourSelector, minuteSelector, secondSelector;
        
        /**
         * 
         */
        public TimeSelectorPanel(){
            super();
            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            lblDays = new JLabel("Days");
            lblHours = new JLabel("Hours");
            lblMinutes = new JLabel("Minutes");
            lblSeconds = new JLabel("Seconds");
            daySelector = new SelectorPanel(100);
            hourSelector = new SelectorPanel(24);
            minuteSelector = new SelectorPanel(60);
            secondSelector = new SelectorPanel(60);
            
            layout.setHorizontalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblDays)
                        .addComponent(daySelector))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblHours)
                        .addComponent(hourSelector))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblMinutes)
                        .addComponent(minuteSelector))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblSeconds)
                        .addComponent(secondSelector))
            );
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDays)
                        .addComponent(lblHours)
                        .addComponent(lblMinutes)
                        .addComponent(lblSeconds))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(daySelector)
                        .addComponent(hourSelector)
                        .addComponent(minuteSelector)
                        .addComponent(secondSelector))
            );
        }
        
        /**
         * Returns the time represented by all four textfields
         * represented in milliseconds.
         * @return
         */
        public long getTime(){
            long result = daySelector.getTime() * 1000 * 60 * 60 * 24;
            result += hourSelector.getTime() * 1000 * 60 * 60;
            result += minuteSelector.getTime() * 1000 * 60;
            result += secondSelector.getTime() * 1000;
            return result;
        }
    }
    
    /**
     * A panel containing a plus- and minus-button and a textfield with
     * a number. Pressing the plus button increments the value in the 
     * textfield by one, while pressing the minus button decrements the
     * value by one.
     * If the value gets larger than the given crossover point the value
     * wraps around to zero. If a value gets smaller than 0 it wraps around
     * to the larges value below the crossover point.
     * @author Malik Lund
     *
     */
    private class SelectorPanel extends JPanel implements ActionListener {
        private int crossover;
        private JTextField txfNumber;
        private JButton btnPlus, btnMinus;
        
        /**
         * 
         * @param crossover Value where the number crosses over
         */
        public SelectorPanel(int crossover){
            super();
            this.crossover = crossover;
            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            txfNumber = new JTextField("0");
            btnPlus = new JButton("+");
            btnMinus = new JButton("-");
            
            txfNumber.setHorizontalAlignment(JTextField.RIGHT);
            
            btnPlus.addActionListener(this);
            btnMinus.addActionListener(this);
            
            layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(btnPlus)
                    .addComponent(txfNumber)
                    .addComponent(btnMinus)
            );
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(btnPlus, 20, 20, 20)
                    .addComponent(txfNumber)
                    .addComponent(btnMinus, 20, 20, 20)
            );
        }
        
        /**
         * 
         * @return The the number in the text field as a long.
         * @throws NumberFormatException
         */
        public long getTime() throws NumberFormatException{
            return Long.parseLong(txfNumber.getText());
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            /**
             * Increment button.
             */
            if (ae.getSource() == btnPlus){
                int number = getNumber();
                number = (number + 1) % crossover;
                txfNumber.setText(number + "");
            }
            
            /**
             * Decrement button.
             */
            else if (ae.getSource() == btnMinus){
                int number = getNumber();
                number = (number - 1);
                if (number < 0){
                    number = crossover - 1;
                }
                txfNumber.setText(number + "");
            }
        }
        
        /**
         * Performs various safety checks on the value in the
         * text field.
         * @return
         * @throws NumberFormatException
         */
        private int getNumber() throws NumberFormatException{
            int number = 0;
            if (txfNumber.getText().length() > 0){
                number = Integer.parseInt(txfNumber.getText());
            } else {
                number = 0;
            }
            
            if (number >= crossover){
                number = crossover - 1;
            }
            if (number < 0){
                number = 0;
            }
            return number;
        }
    }
}
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.LongToStringParser;
import carletti.model.SubTreatment;
import carletti.model.Treatment;

/**
 * This class is used by the JTable in CreateNewSubTreatmentDialog.
 * It contains the current list of SubTreatment-objects to be added to a
 * new Treatment-object in a new Product-object.
 * @author Malik Lund
 *
 */
public class NewProductSubTreatmentsTableModel extends AbstractTableModel {
    // Column headers.
    private String[] coloumnNames = {"Name", "Minimum", "Optimal", "Maximum"};
    // The actual data.
    private Treatment treatment = new Treatment("temp");

    @Override
    public int getColumnCount() {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount() {
        return treatment.getSubTreatments().size();
    }
    
    @Override
    public String getColumnName(int col){
        return coloumnNames[col];
    }

    @Override
    /**
     * @return col 1: Name, col 2: Min, col 3: optimal, col 4: max
     */
    public Object getValueAt(int row, int col) {
        List<SubTreatment> subTreatments = treatment.getSubTreatments();
        if (col < 1){
            return subTreatments.get(row).getName();
        } else {
            long time;
            if (col == 1){
                time = subTreatments.get(row).getDryMin();
            }
            else if (col == 2){
                time = subTreatments.get(row).getDryPrime();
            } else {
                time = subTreatments.get(row).getDryMax();
            }
            return LongToStringParser.parseLongToString(time);
        }
    }
    
    @Override
    /**
     * Simply returns the class-type of each column.
     */
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }

    /**
     * Add a new SubTreatment to the list of subTreatments.
     * @param name Name of the subtreatment.
     * @param min Minimum drying time.
     * @param optimal Optimal or prime drying time.
     * @param max Maximum drying time.
     */
    public void newSubTreatment(String name, long min, long optimal, long max) {
        treatment.createSubTreatment(name, min, optimal, max);
        fireTableDataChanged();
    }
    
    /**
     * Returns a temporary treatment containing the subtreatments
     * @return A Treatment object containing the SubTreatments. OBS! Recreate using
     *         the Service class!! This is only temporary and it will not be saved
     *         in the database!
     */
    public Treatment getData(){
        return treatment;
    }
    
    /**
     * Replace the data in the table with the
     * SubTreatments in the given Treatment.
     * @param subTreatments
     */
    public void setData(Treatment t){
        treatment = t;
        fireTableDataChanged();
    }

}
package carletti.gui.dialogs;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import carletti.model.Position;
import carletti.model.Product;
import carletti.service.Service;

/**
 *@group Lars Nielsen, Malik Lasse Lund, Martin RÃ¸nn Bundgaard
 *@class NewSubProductDialog
 *@programmer Lars Nielsen
 */
/*
 * This class is harcoded due to certain requriments, which couldn't 
 * be achive with JWindowsBuild
 */
public class NewSubProductDialog extends JDialog {
    
    private Service service; // Holds an singelton instance of the Service
    
    //GUI componenst
    private JTextField txfName;
    private JComboBox comboPos;
    private JLabel lblName, lblProd;
    private JScrollPane jspProducts;
    private JList prodList;
    private JButton btnSub, btnCan;
    
    //Start coordinates for the different GUI objects
    private int x = 20, y = 20;
    
    //Dimensions for the GUI components
    private Dimension defaultSize = new Dimension(360, 360);
    private Dimension btnSize = new Dimension(140, 20);
    private Dimension lblSize = new Dimension(140, 20);
    private Dimension txfSize = new Dimension(160, 20);
    private Dimension jspSize = new Dimension(320, 160);

    private Controller btnCtrl; //Holds an instance of the Controller 

    public NewSubProductDialog() {
        
        service = Service.getInstance(); //Service is instantiated 
        
        btnCtrl = new Controller(); //Controller is instantiated 

        //Generel preference  for the JDialog
        this.setSize(defaultSize);
        this.setResizable(false);
        this.setTitle("New Subproduct");
        this.setLayout(null);
        this.setModal(true);

        /*
         * Instansation of all GUI componetens and the preferences 
         */
        lblName = new JLabel();
        lblName.setText("Name");
        lblName.setSize(lblSize);
        lblName.setLocation(x, y);
        this.add(lblName);

        x += 160;

        txfName = new JTextField();
        txfName.setSize(txfSize);
        txfName.setLocation(x, y);
        this.add(txfName);
        
        y += 40;
        
        comboPos = new JComboBox(getPositionsAvailable().toArray());
        comboPos.setSize(txfSize);
        comboPos.setLocation(x, y);
        this.add(comboPos);

        x = 20;
        

        lblProd = new JLabel();
        lblProd.setText("Products");
        lblProd.setSize(lblSize);
        lblProd.setLocation(x, y);
        this.add(lblProd);

        y += 40;

        prodList = new JList();
        prodList.setListData(service.getProducts().toArray());
        prodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jspProducts = new JScrollPane(prodList);
        jspProducts.setSize(jspSize);
        jspProducts.setLocation(x, y);
        this.add(jspProducts);

        y += 180;

        btnSub = new JButton("Submit");
        btnSub.addActionListener(btnCtrl);
        btnSub.setSize(btnSize);
        btnSub.setLocation(x, y);
        this.add(btnSub);

        x += 180;

        btnCan = new JButton("Cancel");
        btnCan.addActionListener(btnCtrl);
        btnCan.setSize(btnSize);
        btnCan.setLocation(x, y);
        this.add(btnCan);

    }

    /**
     * Allows to set the visibility in the Controller class
     * @param visibility
     */
    private void setDialogVisibility(boolean visibility) {
        this.setVisible(visibility);
    }
    
    /**
     * Used to get all positions which is not occurpied by a SubProudct
     * @return
     */
    private List<Position> getPositionsAvailable(){
        
        ArrayList<Position> avaPos = new ArrayList<Position>();
        List<Position> allPos = service.getPositions();
        
        for(int i = 0; i < allPos.size(); i++){
            if(allPos.get(i).getSubProduct() == null){
                avaPos.add(allPos.get(i));
            }
        }
        return avaPos;
    }

    /**
     * The inner class Controller, is orgnaised so we can controll which button
     * events is actived
     */
    private class Controller implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource().equals(btnSub)) {
                String name = txfName.getText();
                Product p = (Product) prodList.getSelectedValue();
                Position pos = (Position) comboPos.getSelectedItem();
                
                service.createSubProduct(name, p, pos);
                NewSubProductDialog.this.setVisible(false);

            } else if (ae.getSource().equals(btnCan)) {
                setDialogVisibility(false);
            }
        }
    }   
}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import carletti.gui.*;
import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import carletti.gui.UpdaterThread;
import carletti.model.Position;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;
import carletti.dao.JpaDao;

public class NextSubTreatmentDialog extends JFrame
{
    private Service service;
    
    private Dimension minsize = new Dimension(600, 400);
    private JTable nextSubProcuctTable;
    private JButton btnNextSubTreatment;
    private JButton btnAnnuller;
    private NextSubTreatmentDialogTableModel tableModel;
    private JScrollPane nextSubProcuctScrollPane;
    private Controller btnCtrl;
    private NextSubTreatmentDialogTableModel nextSubTreatmentDialogTableModel;

    public NextSubTreatmentDialog()
    {
        service = Service.getInstance();
        btnCtrl = new Controller();
        this.setMinimumSize(minsize);

        tableModel = new NextSubTreatmentDialogTableModel();
        nextSubProcuctTable = new JTable(tableModel);

        nextSubProcuctScrollPane = new JScrollPane(nextSubProcuctTable);
        nextSubProcuctScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(nextSubProcuctScrollPane, BorderLayout.CENTER);

        btnNextSubTreatment = new JButton("Next Subtreatment");
        btnNextSubTreatment.addActionListener(btnCtrl);

        btnAnnuller = new JButton("Cancel");
        btnAnnuller.addActionListener(btnCtrl);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        .addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
        .createParallelGroup(Alignment.TRAILING).addComponent(nextSubProcuctScrollPane,
        GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE).addGroup(groupLayout
        .createSequentialGroup()
        .addComponent(btnNextSubTreatment)
        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(btnAnnuller)))
        .addGap(0)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
        .addComponent(nextSubProcuctScrollPane, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        .addPreferredGap(ComponentPlacement.RELATED)
        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        .addComponent(btnNextSubTreatment)
        .addComponent(btnAnnuller))
        .addGap(3)));
        
        getContentPane().setLayout(groupLayout);

        this.setVisible(true);
    }

    public void update()
    {

    }

    private class Controller implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {

            if (ae.getSource().equals(btnAnnuller))
            {
                NextSubTreatmentDialog.this.setVisible(false);
            }

            else if (ae.getSource().equals(btnNextSubTreatment))
            {
                int i = nextSubProcuctTable.getSelectedRow();
                if (i < 0)
                {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                } 
                else {
                    // --- Malik Lund start---
                    SubProduct sp = service.getSubProducts(State.TREATMENT).get(i);
                    
                    if (sp.getCurrentSubTreatmentIndex() < sp.getSubtreatments().size() - 1){
                        Position p = new Position("null");
                        PositionSelectionDialog selectionDialog = new PositionSelectionDialog(p);
                        selectionDialog.setVisible(true);
                        
                        if (!p.getPosID().equals("null")){
                            List<Position> positions = service.getPositions();
                            boolean found = false;
                            int index = 0;
                            Position wantedPosition = null;
                            while (!found && index < positions.size()){
                                wantedPosition = positions.get(i);
                                if (wantedPosition.getPosID().equals(p.getPosID())){
                                    found = true;
                                } else {
                                    i++;
                                }
                            }
                            
                            if (found){
                                service.nextTreatnemt(sp);
                                sp.setPosition(wantedPosition);
                                NextSubTreatmentDialog.this.setVisible(false);
                            }
                        }
                        
                    } else {
                        service.nextTreatnemt(sp);
                        NextSubTreatmentDialog.this.setVisible(false);
                    }
                    // --- Malik Lund end ---
                }
            }
        }
    }
    
    /**
     * 
     * @author Malik Lund
     */
    private class PositionSelectionDialog extends JDialog implements ActionListener{
        private Position position;
        
        private JPanel mainPanel, buttonsPanel;
        private JComboBox comboBox;
        private JButton btnOk, btnCancel;
        
        public PositionSelectionDialog(Position position){
            this.position = position;
            this.setLocationRelativeTo(NextSubTreatmentDialog.this);
            this.setModal(true);
            
            mainPanel = new JPanel();
            
            GroupLayout layout = new GroupLayout(this.getContentPane());
            this.getContentPane().setLayout(layout);
            
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            
            btnOk = new JButton("Ok");
            btnCancel = new JButton("Cancel");
            comboBox = new JComboBox(getPositionsAvailable().toArray());
            
            buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
            buttonsPanel.add(btnOk);
            buttonsPanel.add(btnCancel);
            
            btnOk.addActionListener(this);
            btnCancel.addActionListener(this);
            
            layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(comboBox)
                    .addComponent(buttonsPanel)
            );
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(comboBox)
                    .addComponent(buttonsPanel)
            );
            
            this.pack();
        }
        
        private List<Position> getPositionsAvailable(){
            
            ArrayList<Position> avaPos = new ArrayList<Position>();
            List<Position> allPos = service.getPositions();
            
            for(int i = 0; i < allPos.size(); i++){
                if(allPos.get(i).getSubProduct() == null){
                    avaPos.add(allPos.get(i));
                }
            }
            return avaPos;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == btnOk){
                position.setPosID(((Position)comboBox.getSelectedItem()).getPosID());
                this.setVisible(false);
            }
            else if (ae.getSource() == btnCancel){
                this.setVisible(false);
            }
        }
    }
}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;
import carletti.dao.JpaDao;

public class NextSubTreatmentDialogTableModel extends AbstractTableModel
{
    private Service service;
    // Column headers.
    private String[] coloumnNames =
    { "ID", "Name", "Subtreatment", "Number of Subtreatments" };
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State treatment = State.TREATMENT;

    public NextSubTreatmentDialogTableModel()
    {
        service = Service.getInstance();
        newData = service.getSubProducts(treatment);
    }

    @Override
    public int getColumnCount()
    {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return newData.size();
    }

    @Override
    public String getColumnName(int col)
    {
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        SubProduct sp = newData.get(row);
        Object[] value =
        {

        new Integer(sp.getId()), sp.getName(),
                sp.getCurrentSubTreatment().getName(),
                sp.getSubtreatments().size() };
        return value[col];
    }

    @Override
    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    public SubProduct selctedSubProduct(int selectedRow)
    {
        if ( selectedRow >= 0)
        {
            return newData.get(selectedRow);
        }
        else
            return null;
    }

    public void updateData()
    {
        newData = service.getSubProducts(treatment);
    }
        
}
package carletti.gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;

/**
 * Handles the selection of an existing treatment plan.
 * This treatment plan is then associated with a
 * product in creation.
 * @author Malik Lund
 */
public class SelectTreatmentDialog extends JDialog {
    
    private Service service;
    private NewProductSubTreatmentsTableModel tableModel;
    private Controller controller;
    private List<Treatment> treatments;
    
    private JPanel buttonsPanel;
    private JLabel lblTreatments, lblSubTreatments;
    private JList treatmentsList;
    private JScrollPane treatmentsListScrollPane, subTreatmentsTableScrollPane;
    private JTable subTreatmentsTable;
    private JButton btnSelect, btnCancel;
    
    public SelectTreatmentDialog(NewProductSubTreatmentsTableModel tableModel){
        this.setModal(true);
        this.setTitle("Select treatment");
        
        service = Service.getInstance();
        this.tableModel = tableModel;
        controller = new Controller();
        
        lblTreatments = new JLabel("Treatments:");
        treatments = service.getTreatments();
        String[] treatmentsArray = new String[treatments.size()];
        for (int i = 0; i < treatmentsArray.length; i++){
            treatmentsArray[i] = treatments.get(i).getName();
        }
        treatmentsList = new JList(treatmentsArray);
        treatmentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treatmentsList.addListSelectionListener(controller);
        treatmentsListScrollPane = new JScrollPane(treatmentsList);

        lblSubTreatments = new JLabel("Subtreatments");
        subTreatmentsTable = new JTable(tableModel);
        subTreatmentsTableScrollPane = new JScrollPane(subTreatmentsTable);

        buttonsPanel = new JPanel();
        btnSelect = new JButton("Select");
        btnSelect.addActionListener(controller);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(controller);
        buttonsPanel.add(btnSelect);
        buttonsPanel.add(btnCancel);
        
        
        // ----------------
        // Main layout
        
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblTreatments)
                    .addComponent(treatmentsListScrollPane)
                    .addComponent(buttonsPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblSubTreatments)
                    .addComponent(subTreatmentsTableScrollPane))
        );
            
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTreatments)
                    .addComponent(lblSubTreatments))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(treatmentsListScrollPane)
                    .addComponent(subTreatmentsTableScrollPane))
                .addComponent(buttonsPanel)
        );
        
        // ---------------------
        // buttons
        GroupLayout buttonsLayout = new GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsLayout);
        buttonsLayout.setAutoCreateGaps(true);
        buttonsLayout.setAutoCreateContainerGaps(true);
        
        buttonsLayout.setHorizontalGroup(
            buttonsLayout.createSequentialGroup()
                .addComponent(btnSelect)
                .addComponent(btnCancel)
        );
        
        buttonsLayout.setVerticalGroup(
            buttonsLayout.createSequentialGroup()
                .addGroup(buttonsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelect)
                    .addComponent(btnCancel))
        );
        
        this.pack();

    }
    
    private class Controller implements ActionListener, ListSelectionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            /**
             * Select button
             */
            if (ae.getSource() == btnSelect){
                SelectTreatmentDialog.this.setVisible(false);
            }
            /**
             * Cancel button
             */
            else if (ae.getSource() == btnCancel){
                tableModel.setData(new Treatment("Temp")); // "reset" table
                SelectTreatmentDialog.this.setVisible(false);
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            /**
             * Update table with selected treatment from the list.
             */
            if (lse.getSource() == treatmentsList){
                int index = treatmentsList.getSelectedIndex();
                if (index >= 0){
                    Treatment t = treatments.get(index);
                    if (t.getName().equals(treatmentsList.getSelectedValue())){
                        tableModel.setData(t);
                    }
                }
            }
        }
    }
}
/**
 * @author Martin
 */

package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import carletti.service.Service;

public class ShowDoneDialog extends JFrame
{
    
    private Dimension minsize = new Dimension(600, 400);
    private Service service;
    private Controller btnCtrl;
    private JButton btnOk;
    private JScrollPane scrollPane;
    private ShowDoneDialogTableModel tableModel;
    private JTable doneTable;
    
    public ShowDoneDialog()
    {
        
        btnCtrl = new Controller();
        service = Service.getInstance();
        this.setMinimumSize(minsize);

        tableModel = new ShowDoneDialogTableModel();
        doneTable = new JTable(tableModel);

        scrollPane = new JScrollPane(doneTable);
        scrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        btnOk = new JButton("OK");
        btnOk.addActionListener(btnCtrl);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                    .addGap(0))
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnOk)
                    .addContainerGap(527, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnOk)
                    .addContainerGap())
        );
        
        getContentPane().setLayout(groupLayout);

        this.setVisible(true);
        
    }

    private class Controller implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            if (ae.getSource().equals(btnOk))
            {
                ShowDoneDialog.this.setVisible(false);
            }
        }
    }       
}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class ShowDoneDialogTableModel extends AbstractTableModel
{
    private Service service;
    // Column headers.
    private String[] coloumnNames =
    { "ID", "Name", "Subtreatment", "Number of Subtreatments" };
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State done = State.DONE;

    public ShowDoneDialogTableModel()
    {
        service = Service.getInstance();
        newData = service.getSubProducts(done);
    }

    @Override
    public int getColumnCount()
    {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return newData.size();
    }

    @Override
    public String getColumnName(int col)
    {
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        SubProduct sp = newData.get(row);
        Object[] value =
        {

        new Integer(sp.getId()), sp.getName(),
                sp.getCurrentSubTreatment().getName(),
                sp.getSubtreatments().size() };
        return value[col];
    }

    @Override
    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    public SubProduct selctedSubProduct(int selectedRow)
    {
        if ( selectedRow >= 0)
        {
            return newData.get(selectedRow);
        }
        else
            return null;
    }

    public void updateData()
    {
        newData = service.getSubProducts(done);
    }
}
/**
 * @author Martin
 */

package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import carletti.service.Service;


public class ShowWastedDialog extends JFrame
{
    
    private Dimension minsize = new Dimension(600, 400);
    private Service service;
    private Controller btnCtrl;
    private JButton btnOk;
    private JScrollPane scrollPane;
    private ShowWastedDialogTableModel tableModel;
    private JTable wastedTable;
    
    public ShowWastedDialog()
    {
    btnCtrl = new Controller();
    service = Service.getInstance();
    this.setMinimumSize(minsize);

    tableModel = new ShowWastedDialogTableModel();
    wastedTable = new JTable(tableModel);

    scrollPane = new JScrollPane(wastedTable);
    scrollPane
            .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    getContentPane().add(scrollPane, BorderLayout.CENTER);

    btnOk = new JButton("OK");
    btnOk.addActionListener(btnCtrl);

    GroupLayout groupLayout = new GroupLayout(getContentPane());
    groupLayout.setHorizontalGroup(
        groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup()
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                .addGap(0))
            .addGroup(groupLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnOk)
                .addContainerGap(527, Short.MAX_VALUE))
    );
    groupLayout.setVerticalGroup(
        groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(groupLayout.createSequentialGroup()
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(btnOk)
                .addContainerGap())
    );
    
    getContentPane().setLayout(groupLayout);

    this.setVisible(true);
    
}

private class Controller implements ActionListener
{
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource().equals(btnOk))
        {
            ShowWastedDialog.this.setVisible(false);
        }
    }
}


}
/**
 * @author Martin
 */
package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class ShowWastedDialogTableModel extends AbstractTableModel
{
    private Service service;
    // Column headers.
    private String[] coloumnNames =
    { "ID", "Name", "Subtreatment", "Number of Subtreatments" };
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State wasted = State.WASTE;

    public ShowWastedDialogTableModel()
    {
        service = Service.getInstance();
        newData = service.getSubProducts(wasted);
    }

    public int getColumnCount()
    {
        return coloumnNames.length;
    }

    public int getRowCount()
    {
        return newData.size();
    }

    public String getColumnName(int col)
    {
        return coloumnNames[col];
    }

    public Object getValueAt(int row, int col)
    {
        SubProduct sp = newData.get(row);
        Object[] value =
        {

        new Integer(sp.getId()), sp.getName(),
                sp.getCurrentSubTreatment().getName(),
                sp.getSubtreatments().size() };
        return value[col];
    }

    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    public SubProduct selctedSubProduct(int selectedRow)
    {
        if ( selectedRow >= 0)
        {
            return newData.get(selectedRow);
        }
        else
            return null;
    }

    public void updateData()
    {
        newData = service.getSubProducts(wasted);
    }
}
package carletti.gui.dialogs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carletti.model.State;
import carletti.model.SubProduct;

/**
 * @group Lars Nielsen, Malik Lasse Lund, Martin RÂ¿n Bundgaard
 * @class SubProductDialog
 * @author Lars Nielsen
 */
public class SubProductDialog extends JDialog{

    private SubProduct sub; //holds the current sub product which is wanted for inspection
    
    /*
     *GUI components 
     */
    private JPanel panel;
    private JLabel labID, labName, labState, labProduct;
    private JTextField txfID, txfName, txfState, txfProd;
    private JButton btnOK;
    private Controller btnCtrl;
    
    public SubProductDialog(SubProduct sub){
        
        this.sub = sub;
        btnCtrl = new Controller();
        
        //Preferences for the dialog 
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
        
        //GUI components and their preferences
        panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new GridLayout(6, 2, 20, 20));
        
        labID = new JLabel();
        labID.setText("ID:");
        panel.add(labID);
        
        txfID = new JTextField();
        txfID.setEditable(false); //User should not be able to change the info from heir
        panel.add(txfID);
        
        labName = new JLabel();
        labName.setText("Name:");
        panel.add(labName);
        
        txfName = new JTextField();
        txfName.setEditable(false);
        panel.add(txfName);
        
        labState = new JLabel();
        labState.setText("State:");
        panel.add(labState);
        
        txfState = new JTextField();
        txfState.setEditable(false);
        panel.add(txfState);
        
        labProduct = new JLabel();
        labProduct.setText("Product");
        panel.add(labProduct);
        
        txfProd = new JTextField();
        txfProd.setEditable(false);
        panel.add(txfProd);
        
        btnOK = new JButton();
        btnOK.setText("OK");
        btnOK.addActionListener(btnCtrl);
        panel.add(btnOK);
        
        setInfo();
        this.pack();
    }
    
    //Sets the info for the JTextFields 
    private void setInfo(){
        txfID.setText(Integer.toString(sub.getId()));
        txfName.setText(sub.getName());
        checkAndSetState();
        txfProd.setText(sub.getProduct().getName());
        
        
    }
    
    //Sets the info for the txfState
    private void checkAndSetState(){
        if(sub.getState() == State.DONE){
            txfState.setText("Done");
        }
        else if(sub.getState() == State.TREATMENT){
            txfState.setText("Treatment");
        }
        else if(sub.getState() == State.DRYING){
            txfState.setText("Drying");
        }
        else if(sub.getState() == State.WASTE){
            txfState.setText("Waste");
        }
    }
    
    //Allows to change the visibility of the dialog in the Controller
    private void thisSetVisible(boolean visiblity){
        this.setVisible(visiblity);
    }
    
    /*
     * Keeps track of the actions on the buttons
     */
    private class Controller implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            // TODO Auto-generated method stub
            if(ae.getSource().equals(btnOK)){
                thisSetVisible(false);
            }
        }
        
    }
}
package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

/**
 * @group Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 * @class WasteSubProduct
 * @author Lars Nielsen
 *
 */
public class WasteSubProduct extends JDialog{
    
    private Service service; //Holds a singelton of the service class
    
    //GUI Components
    private JPanel infoPanel, btnPanel;
    private JButton btnWaste;
    private JButton btnCan;
    private JLabel lblId;
    private JTextField txfID;
    private JLabel lblName;
    private JLabel lblNewLabel;
    private JLabel lblProduct;
    private JTextField txfName;
    private JTextField txfState;
    private JTextField txfProduct;
    
    private SubProduct sub; //Holds the SubProduct, which can be wasted
    
    
    private Controller btnCtrl; //Instance of the controller class
    
    
    public WasteSubProduct(SubProduct sub) {
        
        service = Service.getInstance(); 
        this.sub = sub;
        btnCtrl = new Controller();
        
        //Generel Prefernec for the Dialog
        setTitle("Waste Subproduct");
        setModal(true);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        //GUI Components and their preferens 
        infoPanel = new JPanel();
        getContentPane().add(infoPanel, BorderLayout.CENTER);
        GridBagLayout gbl_infoPanel = new GridBagLayout();
        gbl_infoPanel.columnWidths = new int[]{0, 0, 0};
        gbl_infoPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
        gbl_infoPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_infoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        infoPanel.setLayout(gbl_infoPanel);
        
        lblId = new JLabel("ID:");
        GridBagConstraints gbc_lblId = new GridBagConstraints();
        gbc_lblId.insets = new Insets(0, 0, 5, 5);
        gbc_lblId.anchor = GridBagConstraints.EAST;
        gbc_lblId.gridx = 0;
        gbc_lblId.gridy = 0;
        infoPanel.add(lblId, gbc_lblId);
        
        txfID = new JTextField();
        txfID.setEditable(false);
        GridBagConstraints gbc_txfID = new GridBagConstraints();
        gbc_txfID.insets = new Insets(0, 0, 5, 0);
        gbc_txfID.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfID.gridx = 1;
        gbc_txfID.gridy = 0;
        infoPanel.add(txfID, gbc_txfID);
        txfID.setColumns(10);
        
        lblName = new JLabel("NAME:");
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.anchor = GridBagConstraints.EAST;
        gbc_lblName.insets = new Insets(0, 0, 5, 5);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 1;
        infoPanel.add(lblName, gbc_lblName);
        
        txfName = new JTextField();
        txfName.setEditable(false);
        GridBagConstraints gbc_txfName = new GridBagConstraints();
        gbc_txfName.insets = new Insets(0, 0, 5, 0);
        gbc_txfName.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfName.gridx = 1;
        gbc_txfName.gridy = 1;
        infoPanel.add(txfName, gbc_txfName);
        txfName.setColumns(10);
        
        lblNewLabel = new JLabel("STATE:\n");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 2;
        infoPanel.add(lblNewLabel, gbc_lblNewLabel);
        
        txfState = new JTextField();
        txfState.setEditable(false);
        GridBagConstraints gbc_txfState = new GridBagConstraints();
        gbc_txfState.insets = new Insets(0, 0, 5, 0);
        gbc_txfState.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfState.gridx = 1;
        gbc_txfState.gridy = 2;
        infoPanel.add(txfState, gbc_txfState);
        txfState.setColumns(10);
        
        lblProduct = new JLabel("PRODUCT");
        GridBagConstraints gbc_lblProduct = new GridBagConstraints();
        gbc_lblProduct.anchor = GridBagConstraints.EAST;
        gbc_lblProduct.insets = new Insets(0, 0, 0, 5);
        gbc_lblProduct.gridx = 0;
        gbc_lblProduct.gridy = 3;
        infoPanel.add(lblProduct, gbc_lblProduct);
        
        txfProduct = new JTextField();
        GridBagConstraints gbc_txfProduct = new GridBagConstraints();
        gbc_txfProduct.fill = GridBagConstraints.HORIZONTAL;
        gbc_txfProduct.gridx = 1;
        gbc_txfProduct.gridy = 3;
        infoPanel.add(txfProduct, gbc_txfProduct);
        txfProduct.setColumns(10);
        
        btnPanel = new JPanel();
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        btnPanel.setLayout(new GridLayout(1, 2, 0, 0));
        
        btnWaste = new JButton("Waste");
        btnWaste.addActionListener(btnCtrl);
        btnPanel.add(btnWaste);
        
        btnCan = new JButton("Cancel");
        btnCan.addActionListener(btnCtrl);
        btnPanel.add(btnCan);
        
        setInfo();
        checkAndSetState();
        
        this.pack();
    }
    
    /**
     * Allows to set the visibility of the dialog, in the Controller class 
     */
    private void desposeDialog(boolean despose){
        this.setVisible(despose);
    }
    
    /**
     * Sets the text of the JTextFields, so one know which SubProduct you are wasting 
     */
    private void setInfo(){
        txfID.setText(Integer.toString(sub.getId()));
        txfName.setText(sub.getName());
        checkAndSetState();
        txfProduct.setText(sub.getProduct().getName());
    }
    
    /**
     * Checks the state of the subproduct and sets the state of the txfState, to the current state
     */
    private void checkAndSetState(){
        if(sub.getState() == State.DONE){
            txfState.setText("Done");
        }
        else if(sub.getState() == State.TREATMENT){
            txfState.setText("Treatment");
        }
        else if(sub.getState() == State.DRYING){
            txfState.setText("Drying");
        }
        else if(sub.getState() == State.WASTE){
            txfState.setText("Waste");
        }
    }
    
    
    /**
     * 
     * The controller class, is controlling which actions is 
     *
     */
    private class Controller implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource().equals(btnWaste)){
                service.changeState(sub, State.WASTE);
                sub.setPosition(null);
                desposeDialog(false);
            }
            else if(ae.getSource().equals(btnCan)){
                desposeDialog(false);
            }
            
        }
        
    }

}
package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import carletti.gui.dialogs.CreateNewProductDialog;
import carletti.gui.dialogs.NewSubProductDialog;
import carletti.gui.dialogs.NextSubTreatmentDialog;
import carletti.gui.dialogs.ShowDoneDialog;
import carletti.gui.dialogs.ShowWastedDialog;
import carletti.gui.dialogs.ShowWastedDialogTableModel;
import carletti.gui.dialogs.SubProductDialog;
import carletti.gui.dialogs.WasteSubProduct;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.table.TableColumn;

/**
 * @group Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 * @class MainFrame
 * @author Lars Nielsen
 *
 */
public class MainFrame extends JFrame {
    
    private Service service; //Holds singelton instance of service

    //GUI components
    private JPanel buttonsPanel;
    private JScrollPane subProductsScrollPane;
    private JButton btnNewSubProduct;
    private JButton btnInfo;
    private JButton btnWaste;
    private JTable subProductTable;
    private NewSubProductTableModel subProductTableModel;
    private Dimension minimumSize = new Dimension(1000, 400);
    private Dimension btnMinSize = new Dimension(20, 180);
    private JButton btnNewProduct;
    private JButton btnProductInfo;
    private JButton btnTreatment;
    private JButton btnShowSubTreatment;
    private JButton btnShowDone;
    private JButton btnShowWasted;

    //Button controller
    private Controller btnCtrl;
    
    public MainFrame() {
        
        service = Service.getInstance();

        btnCtrl = new Controller();
        
        //Frame preferences 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        this.setTitle("Carletti");
        this.setSize(minimumSize);
        this.setLocation(new Point(100, 50));
        buttonsPanel = new JPanel();
        getContentPane().add(buttonsPanel, BorderLayout.EAST);

        //GUI Components preferences 
        btnInfo = new JButton("Info");
        btnInfo.setMinimumSize(btnMinSize);
        btnInfo.addActionListener(btnCtrl);

        btnWaste = new JButton("Waste");
        btnWaste.setMinimumSize(btnMinSize);
        btnWaste.addActionListener(btnCtrl);

        btnProductInfo = new JButton("Product Info");
        btnProductInfo.addActionListener(btnCtrl);

        btnNewSubProduct = new JButton("New subproduct");
        btnNewSubProduct.setMinimumSize(btnMinSize);
        btnNewSubProduct.addActionListener(btnCtrl);

        btnNewProduct = new JButton("New Product");
        
        btnTreatment = new JButton("Treatment");
        btnTreatment.addActionListener(btnCtrl);
        
        btnShowSubTreatment = new JButton("Show Subtreatment");
        btnShowSubTreatment.addActionListener(btnCtrl);
        
        btnShowDone = new JButton("Show Done");
        btnShowDone.addActionListener(btnCtrl);
        
        btnShowWasted = new JButton("Show Wasted");
        btnShowWasted.addActionListener(btnCtrl);
        
        GroupLayout gl_buttonsPanel = new GroupLayout(buttonsPanel);
        gl_buttonsPanel.setHorizontalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addComponent(btnInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnWaste, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnProductInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnNewProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnTreatment, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowSubTreatment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNewSubProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowDone, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowWasted, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );
        gl_buttonsPanel.setVerticalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_buttonsPanel.createSequentialGroup()
                    .addComponent(btnInfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnWaste, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnProductInfo)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewSubProduct, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewProduct)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowSubTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowDone)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowWasted)
                    .addGap(107))
        );
        buttonsPanel.setLayout(gl_buttonsPanel);
        btnNewProduct.addActionListener(btnCtrl);

        // subProductList = new JList();
        // subProductList.setListData(Service.getAllNotWastedSubProducts().toArray());
        // subProductList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        subProductTableModel = new NewSubProductTableModel();
        subProductTable = new JTable(subProductTableModel);
        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subProductTable.setDefaultRenderer(subProductTableModel.getColumnClass(0), new HighlighterCellRenderer());

        subProductsScrollPane = new JScrollPane(subProductTable);
        subProductsScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(subProductsScrollPane, BorderLayout.CENTER);

        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initColumnSizes(subProductTable);
        
        // --- Malik Lund ---
        Thread t = new Thread(new UpdaterThread(this));
        t.start();
        // ------------------

        this.setVisible(true);

    }
    
    /**
     * Hardcode some default column sizes.
     * @author Malik Lund
     * @param table
     */
    private void initColumnSizes(JTable table) {
        NewSubProductTableModel model = (NewSubProductTableModel)table.getModel();
        TableColumn column = null;
        for (int i = 0; i < model.getColumnCount(); i++){
            column = table.getColumnModel().getColumn(i);
            if (i == 1){
                column.setPreferredWidth(125);
            }
            else if (i == 2){
                column.setPreferredWidth(40);
            }
            else if (i == 3){
                column.setPreferredWidth(40);
            }
        }
    }

    /*
     * @author Malik Lund
     */
    public synchronized void updateList() {
        int selection = subProductTable.getSelectedRow();
        subProductTableModel.updateData();
        subProductTableModel.fireTableDataChanged();
        subProductTable.changeSelection(selection, 0, false, false);
    }

    private class Controller implements ActionListener {
        public void actionPerformed(ActionEvent ae) {

            /*
             * Info button
             */
            if (ae.getSource().equals(btnInfo)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                } else {
                    SubProductDialog spd = new SubProductDialog(sp);
                    spd.setLocationRelativeTo(MainFrame.this);
                    spd.setVisible(true);
                }
            }
            /*
             * New subproduct button
             */
            else if (ae.getSource().equals(btnNewSubProduct)) {
                NewSubProductDialog nspd = new NewSubProductDialog();
                nspd.setLocationRelativeTo(MainFrame.this);
                nspd.setVisible(true);
                updateList();
            } 
            /*
             * New product button
             */
            else if (ae.getSource().equals(btnNewProduct)) {
                CreateNewProductDialog newProductDialog = new CreateNewProductDialog();
                newProductDialog.setLocationRelativeTo(MainFrame.this);
                newProductDialog.setVisible(true);
            } 
            /*
             * Waste button
             */
            else if (ae.getSource().equals(btnWaste)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }

                else {
                    WasteSubProduct wsp = new WasteSubProduct(sp);
                    wsp.setLocationRelativeTo(MainFrame.this);
                    wsp.setVisible(true);
                    updateList();
                }

            }
            /*
             * Treatment button
             */
            else if (ae.getSource().equals(btnTreatment)){
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) 
                {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }
                else
                {
                    service.changeState(sp, State.TREATMENT);
                    sp.setPosition(null);
                }
                updateList();
            }
            /*
             * Product info button
             */
            else if (ae.getSource().equals(btnProductInfo)) {
                ShowProductFrame spf = new ShowProductFrame();
                spf.setLocationRelativeTo(MainFrame.this);
                spf.setVisible(true);
            }
            
            /*
             * Show subtreatment button
             */
            else if (ae.getSource().equals(btnShowSubTreatment))
            {
                NextSubTreatmentDialog ntd = new NextSubTreatmentDialog();
                ntd.setLocationRelativeTo(MainFrame.this);
                ntd.setVisible(true);
            }
            /*
             * Show done button
             */
            else if (ae.getSource().equals(btnShowDone))
            {
                ShowDoneDialog sdd = new ShowDoneDialog();
                sdd.setLocationRelativeTo(MainFrame.this);
                sdd.setVisible(true);
            }
            /*
             * Show wasted button
             */
            else if (ae.getSource().equals(btnShowWasted))
            {
                ShowWastedDialog swd = new ShowWastedDialog();
                swd.setLocationRelativeTo(MainFrame.this);
                swd.setVisible(true);
                
            }
        }
    }
    
    /**
     * Custom cell renderer that colors the "time remaining" cell
     * white, green, yellow or red depending on the time remaining
     * until max drying time is exceeded.
     * 
     * @author Malik Lund
     */
    private class HighlighterCellRenderer extends DefaultTableCellRenderer{
        private DefaultTableCellRenderer defaultRenderer;
        
        public HighlighterCellRenderer(){
            super();
            defaultRenderer = new DefaultTableCellRenderer();
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            SubProduct subProducts = subProductTableModel.getSubProduct(row);
            
            long currentTime = System.currentTimeMillis();
            long minTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMin();
            long optimalTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryPrime();
            long maxTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMax();
            
            // Set color
            if (minTime < currentTime && optimalTime > currentTime && column == 0){
                comp.setBackground(Color.green);
            } 
            else if (optimalTime < currentTime && maxTime > currentTime && column == 0){
                comp.setBackground(Color.yellow);
            }
            else if (currentTime > maxTime && column == 0) {
                comp.setBackground(Color.red);
            } else {
                // For reasons I don't understand and don't have time to look into
                // it is necessary to have two different renderers. The color change is
                // apparently persistent.
                return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
            
            return comp;
        }
    }
}
package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import carletti.gui.dialogs.CreateNewProductDialog;
import carletti.gui.dialogs.NewSubProductDialog;
import carletti.gui.dialogs.NextSubTreatmentDialog;
import carletti.gui.dialogs.ShowDoneDialog;
import carletti.gui.dialogs.ShowWastedDialog;
import carletti.gui.dialogs.ShowWastedDialogTableModel;
import carletti.gui.dialogs.SubProductDialog;
import carletti.gui.dialogs.WasteSubProduct;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import javax.swing.table.TableColumn;


public class MainFrameTabbed extends JFrame {
    private Service service;

    private JPanel buttonsPanel;
    private JScrollPane subProductsScrollPane, subProductsTreatmentScrollPane;
    private JButton btnNewSubProduct;
    private JButton btnInfo;
    private JButton btnWaste;
    private JTabbedPane tabbedPane;
    private JTable subProductTable, subProductsTreatmentTable;
    private NewSubProductTableModelTabbed subProductTableModel, subProductsTreatmentTableModel;

    private Controller btnCtrl;

    private Dimension minimumSize = new Dimension(1000, 400);
    private Dimension btnMinSize = new Dimension(20, 180);
    private JButton btnNewProduct;
    private JButton btnProductInfo;
    private JButton btnTreatment;
    private JButton btnShowSubTreatment;
    private JButton btnShowDone;
    private JButton btnShowWasted;

    public MainFrameTabbed() {
        service = Service.getInstance();

        btnCtrl = new Controller();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        this.setTitle("Carletti");
        this.setSize(minimumSize);
        this.setLocation(new Point(100, 50));
        buttonsPanel = new JPanel();
        getContentPane().add(buttonsPanel, BorderLayout.EAST);

        btnInfo = new JButton("Info");
        btnInfo.setMinimumSize(btnMinSize);
        btnInfo.addActionListener(btnCtrl);

        btnWaste = new JButton("Waste");
        btnWaste.setMinimumSize(btnMinSize);
        btnWaste.addActionListener(btnCtrl);

        btnProductInfo = new JButton("Product Info");
        btnProductInfo.addActionListener(btnCtrl);

        btnNewSubProduct = new JButton("New subproduct");
        btnNewSubProduct.setMinimumSize(btnMinSize);
        btnNewSubProduct.addActionListener(btnCtrl);

        btnNewProduct = new JButton("New Product");
        
        btnTreatment = new JButton("Treatment");
        btnTreatment.addActionListener(btnCtrl);
        
        btnShowSubTreatment = new JButton("Show Subtreatment");
        btnShowSubTreatment.addActionListener(btnCtrl);
        
        btnShowDone = new JButton("Show Done");
        btnShowDone.addActionListener(btnCtrl);
        
        btnShowWasted = new JButton("Show Wasted");
        btnShowWasted.addActionListener(btnCtrl);
        
        GroupLayout gl_buttonsPanel = new GroupLayout(buttonsPanel);
        gl_buttonsPanel.setHorizontalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addComponent(btnInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnWaste, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnProductInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnNewProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnTreatment, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowSubTreatment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNewSubProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowDone, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addComponent(btnShowWasted, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );
        gl_buttonsPanel.setVerticalGroup(
            gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_buttonsPanel.createSequentialGroup()
                    .addComponent(btnInfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnWaste, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnProductInfo)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewSubProduct, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnNewProduct)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowSubTreatment)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowDone)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnShowWasted)
                    .addGap(107))
        );
        buttonsPanel.setLayout(gl_buttonsPanel);
        btnNewProduct.addActionListener(btnCtrl);
        
        tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // --- Drying table ---
        subProductTableModel = new NewSubProductTableModelTabbed(State.DRYING);
        subProductTable = new JTable(subProductTableModel);
        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subProductTable.setDefaultRenderer(subProductTableModel.getColumnClass(0), new HighlighterCellRenderer());

        subProductsScrollPane = new JScrollPane(subProductTable);
        subProductsScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//      getContentPane().add(subProductsScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Drying", subProductsScrollPane);

        subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initColumnSizes(subProductTable);
        
        // --- In treatment table ---
        subProductsTreatmentTableModel = new NewSubProductTableModelTabbed(State.TREATMENT);
        subProductsTreatmentTable = new JTable(subProductsTreatmentTableModel);
        subProductsTreatmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subProductsTreatmentTable.setDefaultRenderer(subProductTableModel.getColumnClass(0), new HighlighterCellRenderer());
        
        subProductsTreatmentScrollPane = new JScrollPane(subProductsTreatmentTable);
        subProductsTreatmentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        tabbedPane.addTab("In treatment", subProductsTreatmentScrollPane);
        
        // --- Malik Lund start---
        Thread t = new Thread(new UpdaterThreadForTabbedMain(this));
        t.start();
        // -------------- end ----

        this.setVisible(true);

    }
    
    /**
     * Hardcode some default column sizes.
     * @author Malik Lund
     * @param table
     */
    private void initColumnSizes(JTable table) {
        NewSubProductTableModelTabbed model = (NewSubProductTableModelTabbed)table.getModel();
        TableColumn column = null;
        for (int i = 0; i < model.getColumnCount(); i++){
            column = table.getColumnModel().getColumn(i);
            if (i == 1){
                column.setPreferredWidth(125);
            }
            else if (i == 2){
                column.setPreferredWidth(40);
            }
            else if (i == 3){
                column.setPreferredWidth(40);
            }
        }
    }

    /*
     * @author Malik Lund
     */
    public synchronized void updateList() {
        int selection = subProductTable.getSelectedRow();
        subProductTableModel.updateData();
        subProductTableModel.fireTableDataChanged();
        subProductTable.changeSelection(selection, 0, false, false);
        
        updateTable(subProductsTreatmentTable);
    }
    
    private synchronized void updateTable(JTable table){
        int selection = table.getSelectedRow();
        NewSubProductTableModelTabbed tableModel = (NewSubProductTableModelTabbed) table.getModel();
        tableModel.updateData();
        tableModel.fireTableDataChanged();
        if (selection >= 0 && selection < tableModel.getColumnCount()){
            table.changeSelection(selection, 0, false, false);
        }
    }

    private class Controller implements ActionListener {
        public void actionPerformed(ActionEvent ae) {

            if (ae.getSource().equals(btnInfo)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                } else {
                    SubProductDialog spd = new SubProductDialog(sp);
                    spd.setLocationRelativeTo(MainFrameTabbed.this);
                    spd.setVisible(true);
                }
            } else if (ae.getSource().equals(btnNewSubProduct)) {
                NewSubProductDialog nspd = new NewSubProductDialog();
                nspd.setLocationRelativeTo(MainFrameTabbed.this);
                nspd.setVisible(true);
                updateList();
            } else if (ae.getSource().equals(btnNewProduct)) {
                CreateNewProductDialog newProductDialog = new CreateNewProductDialog();
                newProductDialog.setLocationRelativeTo(MainFrameTabbed.this);
                newProductDialog.setVisible(true);
            } else if (ae.getSource().equals(btnWaste)) {
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }

                else {
                    WasteSubProduct wsp = new WasteSubProduct(sp);
                    wsp.setLocationRelativeTo(MainFrameTabbed.this);
                    wsp.setVisible(true);
                    updateList();
                }

            }
            
            else if (ae.getSource().equals(btnTreatment)){
                SubProduct sp = subProductTableModel
                        .selctedSubProduct(subProductTable.getSelectedRow());
                if (sp == null) 
                {
                    JOptionPane.showMessageDialog(null,
                            "You need to selected an object");
                }
                else
                {
                    service.changeState(sp, State.TREATMENT);
                }
                updateList();
            }

            else if (ae.getSource().equals(btnProductInfo)) {
                ShowProductFrame spf = new ShowProductFrame();
                spf.setLocationRelativeTo(MainFrameTabbed.this);
                spf.setVisible(true);
            }
            
            else if (ae.getSource().equals(btnShowSubTreatment))
            {
                NextSubTreatmentDialog ntd = new NextSubTreatmentDialog();
                ntd.setLocationRelativeTo(MainFrameTabbed.this);
                ntd.setVisible(true);
            }
            else if (ae.getSource().equals(btnShowDone))
            {
                ShowDoneDialog sdd = new ShowDoneDialog();
                sdd.setLocationRelativeTo(MainFrameTabbed.this);
                sdd.setVisible(true);
            }
            else if (ae.getSource().equals(btnShowWasted))
            {
                ShowWastedDialog swd = new ShowWastedDialog();
                swd.setLocationRelativeTo(MainFrameTabbed.this);
                swd.setVisible(true);
                
            }
        }
    }
    
    /**
     * Custom cell renderer that colors the "time remaining" cell
     * white, green, yellow or red depending on the time remaining
     * until max drying time is exceeded.
     * 
     * @author Malik Lund
     */
    private class HighlighterCellRenderer extends DefaultTableCellRenderer{
        private DefaultTableCellRenderer defaultRenderer;
        
        public HighlighterCellRenderer(){
            super();
            defaultRenderer = new DefaultTableCellRenderer();
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            SubProduct subProducts = subProductTableModel.getSubProduct(row);
            
            long currentTime = System.currentTimeMillis();
            long minTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMin();
            long optimalTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryPrime();
            long maxTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMax();
            
            // Set color
            if (minTime < currentTime && optimalTime > currentTime && column == 0){
                comp.setBackground(Color.green);
            } 
            else if (optimalTime < currentTime && maxTime > currentTime && column == 0){
                comp.setBackground(Color.yellow);
            }
            else if (currentTime > maxTime && column == 0) {
                comp.setBackground(Color.red);
            } else {
                // For reasons I don't understand and don't have time to look into
                // it is necessary to have two different renderers. The color change is
                // apparently persistent.
                return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
            
            return comp;
        }
    }
}
/**
 * @group Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bungaard
 * @class NewSubProductTableModel
 * @author Lars Nielsen
 */
package carletti.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.dao.JpaDao;
import carletti.model.LongToStringParser;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class NewSubProductTableModel extends AbstractTableModel{
    private Service service;
    // Column headers.
    private String[] coloumnNames = {"Time left", "Started on", "ID", "Position", "Name", "State", "Subtreatment", "Subtreatment #"};
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State state = State.DRYING;
        
    public NewSubProductTableModel(){
        service = Service.getInstance();
        newData = service.getSubProducts(state);
    }

    @Override
    public int getColumnCount() {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount() {
        return newData.size();
    }

    @Override
    public String getColumnName(int col){
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        SubProduct sp = newData.get(row);
        Object[] value = {
                LongToStringParser.parseLongToString(sp.timeLeft()), sp.getTime(sp.getTimeAdded()), 
                new Integer(sp.getId()), sp.getPosition().getPosID(), sp.getName(), 
                sp.getState(), sp.getCurrentSubTreatment().getName(), 
                (sp.getCurrentSubTreatmentIndex() + 1) + "/" + sp.getSubtreatments().size() 
        };
        return value[col];
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
    
    public SubProduct selctedSubProduct(int selectedRow){
        if(selectedRow >= 0){
            return newData.get(selectedRow);
        }
        else{
            return null;
        }
        
    }
    
    /**
     * @author Malik Lund
     * @return
     */
    public SubProduct getSubProduct(int row){
        return newData.get(row);
    }

    public void updateData() {
        newData = service.getSubProducts(state);
    }
    

}
/**
 * @group Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bungaard
 * @class NewSubProductTableModel
 * @author Lars Nielsen
 */
package carletti.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.dao.JpaDao;
import carletti.model.LongToStringParser;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class NewSubProductTableModelTabbed extends AbstractTableModel{
    private Service service;
    // Column headers.
    private String[] coloumnNames = {"Time left", "Started on", "ID", "Position", "Name", "State", "Subtreatment", "Subtreatment #"};
    // The actual data.
    private ArrayList<Object[]> data = new ArrayList<Object[]>();
    private List<SubProduct> newData;
    private State state;
        
    public NewSubProductTableModelTabbed(State state){
        service = Service.getInstance();
        this.state = state;
        newData = service.getSubProducts(state);
    }

    @Override
    public int getColumnCount() {
        return coloumnNames.length;
    }

    @Override
    public int getRowCount() {
        return newData.size();
    }

    @Override
    public String getColumnName(int col){
        return coloumnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        SubProduct sp = newData.get(row);
        Object[] value = {
                LongToStringParser.parseLongToString(sp.timeLeft()), sp.getTime(sp.getTimeAdded()), 
                new Integer(sp.getId()), sp.getPosition().getPosID(), sp.getName(), 
                sp.getState(), sp.getCurrentSubTreatment().getName(), 
                (sp.getCurrentSubTreatmentIndex() + 1) + "/" + sp.getSubtreatments().size() 
        };
        return value[col];
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
    
    public SubProduct selctedSubProduct(int selectedRow){
        if(selectedRow >= 0){
            return newData.get(selectedRow);
        }
        else{
            return null;
        }
        
    }
    
    /**
     * @author Malik Lund
     * @return
     */
    public SubProduct getSubProduct(int row){
        return newData.get(row);
    }

    public void updateData() {
        newData = service.getSubProducts(state);
    }
    

}
package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import carletti.service.Service;
import carletti.dao.JpaDao;

public class ShowProductFrame extends JDialog
{
    private Service service;
    private JPanel contentPane;
    private JPanel panel;
    private JButton btnOk;
    private JSplitPane splitPane;
    private JTextPane textPane;
    private JScrollPane scrollPane;
    private JScrollPane screooPane2;
    private JList list;
    private Dimension minimumSize = new Dimension(400, 500);
    
    private Controller btnCtrl;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    ShowProductFrame frame = new ShowProductFrame();
                    frame.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ShowProductFrame()
    {
        service = Service.getInstance();
        
        btnCtrl = new Controller();
        this.setMinimumSize(minimumSize);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);
        
        btnOk = new JButton("OK");
        panel.add(btnOk);
        btnOk.addActionListener(btnCtrl);
        
        splitPane = new JSplitPane();
        contentPane.add(splitPane, BorderLayout.CENTER);
        
        textPane = new JTextPane();
        textPane.setEditable(false);
        splitPane.setRightComponent(textPane);
        
        list = new JList();
        contentPane.add(list, BorderLayout.NORTH);
        list.setListData(service.getProducts().toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                        
                if (e.getValueIsAdjusting())
                    updateTextPane();
            }
            
        });
        
        scrollPane = new JScrollPane(list);
        splitPane.setLeftComponent(scrollPane);
    
        screooPane2 = new JScrollPane(textPane);
        splitPane.setRightComponent(screooPane2);
        
    }
    
    public void updateTextPane()
    {
        int index = list.getSelectedIndex();
        if (index >= 0)
            textPane.setText(service.getProducts().get(list.getSelectedIndex()).getName() + "\n" +
                    service.getProducts().get(list.getSelectedIndex()).getDescription() + "\n" + "Treatment:" + "\n" +
                    service.getProducts().get(list.getSelectedIndex()).getTreatment());
    }
    
    private class Controller implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            
            if(ae.getSource().equals(btnOk)){
                ShowProductFrame.this.setVisible(false);
                }
        }
    }
}
package carletti.gui;

import carletti.gui.dialogs.NextSubTreatmentDialog;

/**
 * Continuously updates the list of SubProducts in MainFrame
 * Obs. keeps running until window is closed.
 * @author Malik Lund
 *
 */
public class UpdaterThread implements Runnable {
    private MainFrame mainFrame;
    private NextSubTreatmentDialog nextSubTreatmentDialog;
    
    public UpdaterThread(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }
    
    public UpdaterThread(NextSubTreatmentDialog nextSubTreatmentDialog)
    {
        this.nextSubTreatmentDialog =  nextSubTreatmentDialog;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true){
            mainFrame.updateList();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
package carletti.gui;

import carletti.gui.dialogs.NextSubTreatmentDialog;

/**
 * Continuously updates the list of SubProducts in MainFrame
 * Obs. keeps running until window is closed.
 * @author Malik Lund
 *
 */
public class UpdaterThreadForTabbedMain implements Runnable {
    private MainFrameTabbed mainFrame;
    private NextSubTreatmentDialog nextSubTreatmentDialog;
    
    public UpdaterThreadForTabbedMain(MainFrameTabbed mainFrame){
        this.mainFrame = mainFrame;
    }
    
    public UpdaterThreadForTabbedMain(NextSubTreatmentDialog nextSubTreatmentDialog)
    {
        this.nextSubTreatmentDialog =  nextSubTreatmentDialog;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true){
            mainFrame.updateList();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
package carletti.model;

/**
 * 
 * @author Malik Lund
 *
 */
public class LongToStringParser {
    
    /**
     * Converts a time given in milliseconds into a string
     * of the form [+/-]DDdHHhMMmSSs where DD represents days, HH represents hours,
     * MM represents minutes and SS represents seconds. 
     * All numbers get padded with a zero if they only consist of a single digit.
     * @param time
     * @return
     */
    public static String parseLongToString(long time){
        boolean negative = false;
        int days, hours, minutes, seconds;
        
        if (time < 0){
            negative = true;
            time = - time;
        }
        
        days = (int) (time / (1000 * 60 * 60 * 24));
        time = time - days * 1000 * 60 * 60 * 24;
        
        hours = (int) (time / (1000 * 60 * 60));
        time = time - hours * 1000 * 60 * 60;
        
        minutes = (int) (time / (1000 * 60));
        time = time - minutes * 1000 * 60;
        
        seconds = (int) (time / (1000));
        
        StringBuffer buffer = new StringBuffer();
        if (negative){
            buffer.append("+");
        } else {
            buffer.append("-");
        }
        buffer.append(padNumber(days) + "d");
        buffer.append(padNumber(hours) + "h");
        buffer.append(padNumber(minutes) + "m");
        buffer.append(padNumber(seconds) + "s");
        
        return buffer.toString();
    }
    
    /**
     * Pads a number with zero if it is below 10
     * @param number
     * @return
     */
    private static String padNumber(int number){
        if (number < 10){
            return "0" + number;
        } else {
            return number + "";
        }
    }
}

package carletti.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
/**
 *@group Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bundgaard
 *@class Position
 *@author Lars Nielsen
 */
public class Position {

    @Id
    @GeneratedValue
    private int id;
    private String posID;
    @OneToOne(mappedBy = "position")
    private SubProduct sp; // Holds which product is on the position
    public Position(String posID) {
        this.posID = posID;
    }
    
    public Position()
    {
        
    }

    public String getPosID() {
        return posID;
    }

    public void setPosID(String posID) {
        this.posID = posID;
    }
    
    public SubProduct getSubProduct(){
        return sp;
    }

    
    /*
     * @precon There can't be removed a SubProduct from a position, where there
     * are non located
     */
    public boolean removeSubProductFromPosition() {
        if (sp == null) {
            return false;
        } else {
            sp = null;
            return true;
        }
    }
    
    /*
     * @precon There can't be put a SubProduct on a position where there already
     * is another SubProduct already position
     */
    public boolean putSubProductOnPosition(SubProduct sp) {
        if (this.sp == null) {
            putSubProductOnPositionUD(sp);
            sp.setPositionUD(this);
            return true;
        } else {
            return false;
        }
    }

    
    void putSubProductOnPositionUD(SubProduct sp){
        this.sp = sp;
    }
    
    public String toString(){
        return getPosID();
    }
    
    
}
package carletti.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 * Product encapsulates the name,
 * description and treatment of each sub-product.
 * 
 * @author Malik Lund
 *
 */
@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    @OneToOne(cascade={CascadeType.PERSIST})
    private Treatment treatment;
    
    /**
     * Create a Product-object. Id is generated automatically.
     * @param name A short name for the product.
     * @param description A description of the product.
     * @param treatment A treatment plan for the product.
     */
    public Product(String name, String description, Treatment treatment) {
        this.name = name;
        this.description = description;
        this.treatment = treatment;
    }
    
    /**
     * Empty constructor for JPA.
     */
    public Product()
    {
        
    }

    /**
     * Get the treatment associated with this product
     * type.
     * @return The Treatment object associated with this product type.
     */
    public Treatment getTreatment() {
        return treatment;
    }

    /**
     * Replace the treatment associated with this product
     * type.
     * @param treatment The new Treatment object to be associated with this product type.
     */
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    /**
     * Get the id given by the database.
     * @return This objects id represented by an int value.
     */
    public int getId(){
        return id;
    }
    
    /**
     * Get the name of this product.
     * @return A String object containing the name.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Replace the name of this product.
     * @param newName The new name as a String object.
     */
    public void setName(String newName){
        this.name = newName;
    }
    
    /**
     * Get a description of the product.
     * @return The description of the product as a String.
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Replace the description of this product.
     * @param newDescription A String object that is to be the new description.
     */
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
    
//  Added by Martin
    public String toString()
    {
        return name;
    }
//
}
package carletti.model;
/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bundgaard
 * @author: Lars Nielsen
 * @enum-class: State
 */
public enum State { TREATMENT, DRYING, DONE, WASTE}
package carletti.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bundgaard
 * @author: Lars Nielsen
 * @class: SubProduct
 */
public class SubProduct implements Comparable<SubProduct> {

    @Id
    @GeneratedValue
    private int id; // To have a key for the database
    private String name;
    @Enumerated(EnumType.STRING)
    private State state; // The current state which a SubProduct is in

    @OneToMany
    private List<SubTreatment> subtreatments = new ArrayList<SubTreatment>();
    private int currentSubTreatmentIndex = 0;
    @OneToOne(cascade = { CascadeType.PERSIST })
    private Product product;
    private long timeAdded;
    @OneToOne(cascade={CascadeType.PERSIST})
    private Position position;

    public SubProduct(String name, Product product, long timeAdded,
            Position position) {

        this.name = name;
        this.state = State.DRYING;
        this.setProduct(product);
        this.timeAdded = timeAdded;
        this.setPosition(position);
    }

    public SubProduct() {

    }

    public long getTimeAdded() {
        return timeAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<SubTreatment> getSubtreatments() {
        return new ArrayList<SubTreatment>(subtreatments);
    }

    private void setSubtreatments(List<SubTreatment> subtreatments) {
        this.subtreatments = subtreatments;
        setCurrentSubTreatmentIndex(0);
    }

    public int getCurrentSubTreatmentIndex() {
        return currentSubTreatmentIndex;
    }

    public void setCurrentSubTreatmentIndex(int currentSubTreatmentIndex) {
        this.currentSubTreatmentIndex = currentSubTreatmentIndex;
    }

    public Product getProduct() {
        return product;
    }

    private void setProduct(Product product) {
        this.product = product;
        setSubtreatments(product.getTreatment().getSubTreatments());
    }

    /*
     * @post subtreatments is in sort by need
     */
    public void nextSubTreatment() {
        if (currentSubTreatmentIndex < (subtreatments.size() - 1)) {
            setCurrentSubTreatmentIndex(getCurrentSubTreatmentIndex() + 1);
            setState(State.DRYING); // Martin
        } else if (currentSubTreatmentIndex >= (subtreatments.size() - 1)) {
            setState(State.DONE);
        }
    }

    public SubTreatment getCurrentSubTreatment() {
        return subtreatments.get(getCurrentSubTreatmentIndex());
    }

    public String getTime(long time) {
        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy '-' HH:mm:ss");
        return sd.format(time);

    }

    /**
     * @author Lars
     * @return
     */

    public Position getPosition() {
        return position;
    }

    void setPositionUD(Position position) {
        this.position = position;
    }

    public void setPosition(Position position) {
        if (position == null){
            this.position.putSubProductOnPositionUD(null);
        } else {
            position.putSubProductOnPositionUD(this);
        }
        setPositionUD(position);
    }

    /**
     * 
     * @author Martin
     */
    @Override
    public String toString() {
        return LongToStringParser.parseLongToString(timeLeft()) + " | " + getTime(timeAdded) + " | "
                + getId() + " " + name + " " + getState() + " " + (getCurrentSubTreatmentIndex() + 1)
                + "" + " / " + getSubtreatments().size();
    }

    /**
     * 
     * @author Martin
     * @return
     */
    public long timeLeft() {
        long max = (getCurrentSubTreatment().getDryMax() + timeAdded - System
                .currentTimeMillis());

        return max;
    }

    /** 
     * 
     * @author Martin
     */
    
//  @Override
//  public int compareTo(SubProduct otherSubProduct) {
//      return (int) (timeLeft() - otherSubProduct.timeLeft());
//  }
    
    @Override
    public int compareTo(SubProduct otherSupProduct)
    {
        long l = (timeLeft() - otherSupProduct.timeLeft());
    
        int result = 0;
        
        if (l < 0)
            result = -1; 
        
        else if (l > 0)
            result = 1;
     
        return result;
        
    }
}
package carletti.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
/**
 * @group: Lars Nielsen, Malik Lasse Lund, Martin RÂ¿nn Bundgaard
 * @author: Lars Nielsen
 * @class: SubTreatment
 */
public class SubTreatment {
    
    /*
     * Holds the drying time for minimum, prime and maximum drying time
     */
    @Id
    @GeneratedValue
    private int id; //Is need for the JPA identification 
    private long dryMin, dryPrime, dryMax;
    private String name;
    
    SubTreatment(String name, long dryMin, long dryPrime, long dryMax){
        this.setName(name);
        this.dryMin = dryMin;
        this.dryPrime = dryPrime;
        this.dryMax = dryMax;
        
    }
    
    public SubTreatment()
    {
        
    }

    public long getDryMin() {
        return dryMin;
    }

    public void setDryMin(long dryMin) {
        this.dryMin = dryMin;
    }

    public long getDryPrime() {
        return dryPrime;
    }

    public void setDryPrime(long dryPrime) {
        this.dryPrime = dryPrime;
    }

    public long getDryMax() {
        return dryMax;
    }

    public void setDryMax(long dryMax) {
        this.dryMax = dryMax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString()
    {
        return "\n" + name + "\n" + "min= " + getDryMin() + "\nprime= " + getDryPrime() + "\nmax= " + getDryMax();
    }
}
package carletti.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;



@Entity
/**
 * Treatment represents the various drying treatments a product 
 * needs to complete before it is a finished product.
 * 
 * @author Malik Lund
 *
 */
public class Treatment {
    
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private List<SubTreatment> subTreatments;
    
    /**
     * 
     * @param name
     */
    
    public Treatment()
    {
        
    }
    
    public Treatment(String name){
        this.name = name;
        subTreatments = new ArrayList<SubTreatment>();
    }
    
    /**
     * Creates and appends a new treatment to the list.
     * @param name The name of the sub-treatment.
     * @param dryMin Minimum drying time.
     * @param dryPrime Optimal or prime drying time.
     * @param dryMax Maximal drying time.
     */
    public void createSubTreatment(String name, long dryMin, long dryPrime, long dryMax){
        SubTreatment st = new SubTreatment(name, dryMin, dryPrime, dryMax);
        subTreatments.add(st);
    }
    
    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }

    /**
     * OBS: Altering the returned list has no effect on the
     *      internal list (though altering each object in the
     *      list have an effect).
     * @return A new List.
     */
    public List<SubTreatment> getSubTreatments() {
        return new ArrayList<SubTreatment>(subTreatments);
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString()
    {
        return name + "\n" + getSubTreatments().toString() + "\n";
    }
}
package carletti.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import carletti.dao.Dao;
import carletti.dao.LocalDao;
import carletti.model.LongToStringParser;
import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * 
 * @author Martin
 * 
 */

public class Service
{

    private Dao dao;
    private static Service serviceInstance;

    private Service(Dao dao)
    {
        this.dao = dao;
    }

    /**
     * 
     * @param dao
     *            The dao to use. OBS: After initial call, dao doesn't change!
     * @return The instance
     * @author Malik Lund
     */
    public static Service getInstance(Dao dao)
    {
        if (serviceInstance == null)
        {
            serviceInstance = new Service(dao);
        }
        return serviceInstance;
    }

    /**
     * Simple getInstance method that defaults to a local, impersistent storage
     * in memory. If a call has been made to getInstance(Dao) an instance with
     * the previously selected Dao is returned.
     * 
     * @return
     */
    public static Service getInstance()
    {
        if (serviceInstance == null)
        {
            serviceInstance = new Service(LocalDao.getInstance());
        }
        return serviceInstance;
    }

    /**
     * 
     * @param name
     *            The name of the sup-product.
     * @param product
     *            The product of the sub-product.
     * @param position
     *            The position of the sub-product.
     * @return Sub-product.
     */
    public SubProduct createSubProduct(String name, Product product,
            Position position)
    {
        SubProduct sp = new SubProduct(name, product,
                System.currentTimeMillis(), position); // TODO Make timestamp an
                                                        // option to the user
        dao.storeSubProduct(sp);

        return sp;
    }

    /**
     * Remove sub-product
     * 
     * @param subProduct
     *            The sub-product that is to be removed.
     */
    public void removeSubproduct(SubProduct subProduct)
    {
        dao.removeSubProduct(subProduct);
    }

    /**
     * Waste sub-product
     * 
     * @param subProduct
     *            The sub-product that is to be wasted
     */
    public void discardSubProduct(SubProduct subProduct)
    {
        State waste = State.WASTE;
        dao.changeStateOfSubProduct(subProduct, waste);
    }

    /**
     * Get sub-product
     * 
     * @param subProduct
     *            The sub-product to be returned.
     * @return Sub-product.
     */
    public SubProduct getSubproduct(SubProduct subProduct)
    {

        int i = dao.getSubProducts().indexOf(subProduct);
        if (i >= 0)
        {
            return dao.getSubProducts().get(i);
        } else
        {
            return null;
        }
    }

    /**
     * 
     * @author Malik Lund
     * @return
     */
    public List<Treatment> getTreatments()
    {
        return dao.getTreatments();
    }

    /**
     * Create treatment.
     * 
     * @param name
     *            The name of the treatment
     * @return Treatment
     */
    public Treatment createTreatment(String name)
    {
        Treatment t = new Treatment(name);
        return t;
    }

    /**
     * remove treatment
     * 
     * @param treatment
     *            The treatment to be removed.
     */
    public void removeTreatment(Treatment treatment)
    {
        dao.removeTreatment(treatment);
    }

    /**
     * Create new Product.
     * 
     * @param name
     *            The name of the product.
     * @param description
     *            A description of the product.
     * @param treatment
     *            The treatment for the product.
     * @return Product.
     */
    public Product createProduct(String name, String description,
            Treatment treatment)
    {
        Product p = new Product(name, description, treatment);
        dao.storeProduct(p);
        return p;
    }

    /**
     * Remove product
     * 
     * @param product
     *            The product to be removed.
     */
    public void removeProduct(Product product)
    {
        dao.removeProduct(product);
    }

    /**
     * Get product.
     * @param product The product to be returned
     * @return Product.
     */
    public Product getProduct(Product product)
    {
        int i = dao.getProducts().indexOf(product);
        if (i >= 0)
        {
            return dao.getProducts().get(i);
        } else
        {
            return null;
        }

    }
    
    /**
     * Next treatment.
     * @param subProduct The sub-product to be sent to the next treatment
     */
    public void nextTreatnemt(SubProduct subProduct)
    {
        if (subProduct.getCurrentSubTreatmentIndex() < (subProduct
                .getSubtreatments().size() - 1))
        {
            subProduct.setCurrentSubTreatmentIndex(subProduct
                    .getCurrentSubTreatmentIndex() + 1);
            dao.changeStateOfSubProduct(subProduct, State.DRYING); // Martin
        } else if (subProduct.getCurrentSubTreatmentIndex() >= (subProduct
                .getSubtreatments().size() - 1))
        {
            dao.changeStateOfSubProduct(subProduct, State.DONE);
        }
    }

    /**
     * Set product done.
     * @param subProduct The subproduct of which the state is to change to done.
     */
    public void subProductDone(SubProduct subProduct)
    {
        State done = State.DONE;
        dao.changeStateOfSubProduct(subProduct, done);
    }

    /**
     * Gets list of all done products.
     * @return
     */
//  public List<SubProduct> showAllDoneProduct()
//  {
//      State done = State.DONE;
//
//      return dao.getSubProducts(done);
//  }
//
    public List<Product> getProducts()
    {
        return dao.getProducts();
    }
//
//  public List<SubProduct> showAllSubProduct()
//  {
//      List<SubProduct> list = dao.getSubProducts();
//
//      Collections.sort(list);
//      return list;
//  }
//
//  public String getInfoAboutSubProduct(SubProduct subProduct)
//  {
//      int i = dao.getSubProducts().indexOf(subProduct);
//      return dao.getSubProducts().get(i) + "";
//  }
//
//  public List<SubProduct> getDryingSubProduct()
//  {
//      State drying = State.DRYING;
//      return dao.getSubProducts(drying);
//  }

    // --------- Malik-------------
    public void createSomeObjects()
    {
        Service s = Service.getInstance();

        s.createPosition("A1");
        s.createPosition("A2");
        s.createPosition("A3");
        s.createPosition("B1");
        s.createPosition("B2");
        s.createPosition("B3");
        s.createPosition("C1");
        s.createPosition("C2");
        s.createPosition("C3");

        Treatment t1 = s.createTreatment("Red chocolate MMs");
        t1.createSubTreatment("1st drying", generateTime(2), generateTime(30),
                generateTime(0, 1));
        t1.createSubTreatment("2nd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        t1.createSubTreatment("3rd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        Product p1 = s.createProduct("Red Chocolate MMs",
                "Info about red chocolate MMs", t1);

        Treatment t2 = s.createTreatment("Liquorice");
        t2.createSubTreatment("1st drying", generateTime(0, 15),
                generateTime(0, 20), generateTime(0, 25));
        t2.createSubTreatment("2nd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        Product p2 = s.createProduct("Liquorice",
                "Liquorice with coloured sugar layer", t2);

        Treatment t3 = s.createTreatment("Coffeebean");
        t3.createSubTreatment("1st drying", generateTime(0, 55),
                generateTime(0, 60), generateTime(0, 10, 1));
        t3.createSubTreatment("2nd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        t3.createSubTreatment("3rd drying", generateTime(0, 35),
                generateTime(0, 40), generateTime(0, 50));
        Product p3 = s.createProduct("Coffee Bean",
                "Coffee paste with a layer of chocolate", t3);

        List<Position> positions = s.getPositions();

        SubProduct sp1 = s.createSubProduct("Foobar", p1, positions.get(0));
        SubProduct sp2 = s.createSubProduct("Barbaz", p2, positions.get(4));
        SubProduct sp3 = s.createSubProduct("Bazfoo", p3, positions.get(8));
    }

    /**
     * Takes a variable number of arguments and generates a time in
     * milliseconds.
     * 
     * The first value represents seconds, the second minutes, the third hours
     * and the fourth hours. Any arguments after that are ignored.
     * 
     * @author Malik Lund
     * @param times
     * @return
     */
    private long generateTime(int... times)
    {
        long time = 0;
        long[] constants =
        { 1000, 1000 * 60, 1000 * 60 * 60, 1000 * 60 * 60 * 24 };
        int roof = Math.min(times.length, 4);
        for (int i = 0; i < roof; i++)
        {
            time += times[i] * constants[i];
        }
        return time;
    }

    // ----------------------------

    
    //--------Martin---------------
    /**
     * Get a list of all sub-product with the given state. 
     * @param state The state for the product to be returned.
     * @return list of products.
     */
    public List<SubProduct> getSubProducts(State state)
    {
        ArrayList<SubProduct> subProduct = new ArrayList<SubProduct>();
        for (int i = 0; i < dao.getSubProducts().size(); i++)
        {
            if (dao.getSubProducts().get(i).getState() == state)
            {
                subProduct.add(dao.getSubProducts().get(i));
            }
        }
        Collections.sort(subProduct);
        return subProduct;
    }
    //--------------------------------

    
    // -----Lars
    public List<Position> getPositions()
    {
        return dao.getPositions();
    }

    public void createPosition(String posID)
    {
        dao.storePosition(new Position(posID));

    }

    // -----

    /**
     * 
     * @param sp
     *            The product of which the state id to change.
     * @param state
     *            The new state of the product.
     */
    public void changeState(SubProduct sp, State state)
    {
        dao.changeStateOfSubProduct(sp, state);
    }
}
package carletti.test.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.Treatment;

/**
 * 
 * @author Malik
 *
 */
public class DaoTest {
    Dao dao = JpaDao.getInstance();

    @Before
    public void setUp() throws Exception {
        
    }
    
    @After
    /**
     * Delete everything before starting new test.
     */
    public void cleanUp(){
        for (SubProduct sp: dao.getSubProducts()){
            dao.removeSubProduct(sp);
        }
        
        for(Product p: dao.getProducts()){
            dao.removeProduct(p);
        }
        
        for (Treatment t: dao.getTreatments()){
            dao.removeTreatment(t);
        }
    }

    @Test
    public void storeTreatmentTest() {
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        dao.storeTreatment(t1);
        List<Treatment> treatments = dao.getTreatments();
        
        assertEquals("Size differs from 1", 1, treatments.size());
        
        assertEquals("Name differs", "TestTreatment", treatments.get(0).getName());
        
        Treatment retrievedTreatment = treatments.get(0);
        
        assertEquals("1st SubTreatmentDiffers", t1.getSubTreatments().get(0), retrievedTreatment.getSubTreatments().get(0));
    }
    
    @Test
    public void removeTreatmentTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        dao.storeTreatment(t1);
        dao.removeTreatment(t1);
        
        assertEquals("List of treatments not empty", 0, dao.getTreatments().size());
    }
    
    @Test
    public void storeProductTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        dao.storeTreatment(t1);
        Product p1 = new Product("Product1", "ProductDescription", t1);
        dao.storeProduct(p1);
        List<Product> products = dao.getProducts();
        
        assertEquals("Size is not 1", 1, products.size());
        
        Product retrievedProduct = dao.getProducts().get(0);
        
        assertEquals("Products differs", p1, retrievedProduct);
        
        assertEquals("Treatment differs", t1, retrievedProduct.getTreatment());
    }
    
    @Test
    public void removeProductTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        Product p1 = new Product("Product1", "ProductDescription", t1);
        dao.storeProduct(p1);
        dao.removeProduct(p1);
        
        assertEquals("Product not deleted", 0, dao.getProducts().size());
    }
    
    @Test
    public void storeSubProductTest(){
        Treatment t1 = new Treatment("TestTreatment");
        t1.createSubTreatment("st1", 1000, 2000, 3000);
        t1.createSubTreatment("st2", 1000, 2000, 3000);
        Product p1 = new Product("Product1", "ProductDescription", t1);
        dao.storeProduct(p1);
        SubProduct sp = new SubProduct("sp1", dao.getProducts().get(0), System.currentTimeMillis(), new Position("2A"));
        dao.storeSubProduct(sp);
        
        assertEquals("List of subproducts differs from 1", 1, dao.getProducts().size());
        
        assertEquals("Subproducts differ", sp, dao.getSubProducts().get(0));
        
        assertEquals("Product differs", p1.getName(), dao.getProducts().get(0).getName());
    }
}
/**
 * @author Lars Nielsen, Mailk Lasse Lund, Martin RÂ¿n Bundgaard
 * @Class PositionTest
 * @programmer Lars Nielsen
 */
package carletti.test.model;

import org.junit.After;
import org.junit.Test;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.Treatment;

public class PositionTest {

    @Test
    public void testPosition() {
        //fail("Not yet implemented");
        Position p = new Position("1A");
        
    }

    @Test
    public void testGetPosID() {
        //fail("Not yet implemented");
        Position p = new Position("1A");
        p.getPosID();
    }

    @Test
    public void testSetPosID() {
//      fail("Not yet implemented");
        Position p = new Position("1A");
        p.setPosID("2A");
    }

    @Test
    public void testPutSubProductOnPosition() {
//      fail("Not yet implemented");
        Treatment tm = new Treatment("Kain");
        Product pd = new Product("name", "description", tm);
        SubProduct sp = new SubProduct("Judas", pd, 10000, new Position("1M"));
        Position p = new Position("1A");
        p.putSubProductOnPosition(sp);
    }

    @Test
    public void testRemoveSubProductFromPosition() {
//      fail("Not yet implemented");
        Treatment tm = new Treatment("Kain");
        Product pd = new Product("name", "description", tm);
        SubProduct sp = new SubProduct("Judas", pd, 10000, new Position("2M"));
        Position p = new Position("1A");
        p.putSubProductOnPosition(sp);
        p.removeSubProductFromPosition();
    }
}
/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Product;
import carletti.model.Treatment;

public class ProductTest
{

    Treatment treatment1;
    Treatment treatment2;
    Product p1;
    
    @Before
    public void setUp() throws Exception
    {
        treatment1 = new Treatment("Treatment1"); 
        
        p1 = new Product("Produkt1", "Hej dette er en test",treatment1);
    }
    
    @Test
    public void Product()
    {
        Product p2 = new Product("produkt2", "test", treatment1);
        assertEquals(02, p2.getId());
        assertEquals("produkt2", p2.getName());
        assertEquals("test", p2.getDescription());
        assertEquals(treatment1, p2.getTreatment());
    }

    @Test
    public void testGetTreatment()
    {
        assertEquals(treatment1, p1.getTreatment());
    }

    @Test
    public void testSetTreatment()
    {
        
        p1.setTreatment(treatment2);
        assertEquals(treatment2, p1.getTreatment());
    }

    @Test
    public void testGetId()
    {
        assertEquals(01, p1.getId());
    }

    @Test
    public void testGetName()
    {
        assertEquals("Produkt1", p1.getName());
    }

    @Test
    public void testSetName()
    {
        p1.setName("Produkt11");
        assertEquals("Produkt11", p1.getName());
    }

    @Test
    public void testGetDescription()
    {
        assertEquals("Hej dette er en test", p1.getDescription());
    }

    @Test
    public void testSetDescription()
    {
        p1.setDescription("Den nye");
        assertEquals("Den nye", p1.getDescription());
    }

}
/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.dao.LocalDao;
import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;
import carletti.model.State;

public class ServiceTest
{

    private Product product;
    private Treatment treatment;
    private SubProduct subproduct;
    private SubProduct subproduct2;
//  private Dao localDao = LocalDao.getInstance();
    private Dao dao = JpaDao.getInstance();
    
    private Position p1;
    private Position p2;
    
    private Service service;
    
    
    @Before
    public void setUp() throws Exception
    {
        service = Service.getInstance(dao);
        treatment = service.createTreatment("testTreatment");
        treatment.createSubTreatment("subTreatment1", 100, 200, 300);
        treatment.createSubTreatment("subTreatment2", 400, 500, 600);
        
        product = service.createProduct("product", "dette er en test", treatment );
        p1 = new Position("1");
        p2 = new Position("2");
        subproduct = service.createSubProduct("Sub", product, p1);
        subproduct2 = service.createSubProduct("Sub2", product, p2);
    }
    
    @After
    public void setAfter()
    {   
        for (SubProduct sp : dao.getSubProducts())
        {
            service.removeSubproduct(sp);
        }
        
        for (Product p : service.getProducts())
        {
            service.removeProduct(p);
        }
            
                
        for (Treatment t : dao.getTreatments())
        {
            service.removeTreatment(t);
        }
        
        

    }

    @Test
    public void testCreateSubProduct()
    {
        SubProduct subProduct = service.createSubProduct("test", product, p1);
        assertEquals("test", service.getSubproduct(subProduct).getName());
        assertEquals(product, service.getSubproduct(subProduct).getProduct());
        assertEquals(p1, service.getSubproduct(subProduct).getPosition());
    }

    @Test
    public void testRemoveSubproduct()
    {
        service.removeSubproduct(subproduct);
        
        assertEquals(null, service.getSubproduct(subproduct));
    }

    @Test
    public void testDiscardSubProduct()
    {
        service.discardSubProduct(subproduct);
        assertEquals(State.WASTE , service.getSubproduct(subproduct).getState());
    }

    @Test
    public void testGetSubproduct()
    {
        assertEquals("Sub2", service.getSubproduct(subproduct2).getName());
        assertEquals(product, service.getSubproduct(subproduct2).getProduct());
    }

    @Test
    public void testCreateTreatment()
    {
        Treatment treatment = service.createTreatment("treatment");
        Product p = service.createProduct("name", "description", treatment);
        
        assertEquals(treatment, service.getProduct(p).getTreatment());
    }

    @Test
    public void testCreateProduct()
    {
        Product product = service.createProduct("name", "description", treatment);
        assertEquals(product, service.getProduct(product));
    }

    @Test
    public void testRemoveProduct()
    {
        Treatment t = service.createTreatment("sadf"); 
        Product product = service.createProduct("asdfdsa", "dsafdsaf", t);
        service.removeProduct(product);
        assertEquals(null, service.getProduct(product));
    }

    @Test
    public void testGetProduct()
    {
        assertEquals(product, service.getProduct(product));
    }

    @Test
    public void testNextTreatnemt()
    {
        assertEquals("subTreatment1", service.getSubproduct(subproduct).getCurrentSubTreatment().getName());
        service.nextTreatnemt(subproduct);
        assertEquals("subTreatment2", service.getSubproduct(subproduct).getCurrentSubTreatment().getName());
    }

    @Test
    public void testSubProductDone()
    {
        service.changeState(subproduct, State.DONE);
        assertEquals(State.DONE, service.getSubproduct(subproduct).getState());
    }

    @Test
    public void testGetProducts()
    {
        assertEquals(1, service.getProducts().size());  
    }

    @Test
    public void testGetSubProducts()
    {
        subproduct.setState(State.DONE);
        subproduct2.setState(State.WASTE);
        assertEquals(1, service.getSubProducts(State.DONE).size());
        assertEquals(1, service.getSubProducts(State.WASTE).size());
    }
    
}
package carletti.test.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;
import carletti.dao.JpaDao;

/**
 * 
 * @author Malik Lund
 *
 */
public class SubProductTest {
    Service service = Service.getInstance(JpaDao.getInstance());
    SubProduct subproduct;
    Treatment treatment;
    Product product;
    Position position;
    long timeAdded;

    @Before
    public void setUp() throws Exception {
        treatment = service.createTreatment("Treatment");
        treatment.createSubTreatment("Subtreatment 1", 10, 20, 30);
        treatment.createSubTreatment("Subtreatment 2", 20, 30, 40);
        treatment.createSubTreatment("Subtreatment 3", 30, 40, 50);
        product = service.createProduct("TestProduct", "Product to test", treatment);
        timeAdded = System.currentTimeMillis();
        position = new Position("A2");
        subproduct = new SubProduct("Name", product, timeAdded, position);
    }

    /**
     * Test getters.
     */
    @Test
    public void testSubProduct() {
        assertEquals("Time differs", timeAdded, subproduct.getTimeAdded());
        assertEquals("Name differs", "Name", subproduct.getName());
        assertEquals("State differs", State.DRYING, subproduct.getState());
        
        List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
        List<SubTreatment> expectedSubTreatments = treatment.getSubTreatments();
        assertEquals("Differing number of subtreatments", expectedSubTreatments.size(), actualSubTreatments.size());
        for (int i = 0; i < expectedSubTreatments.size(); i++){
            assertEquals("Differing SubTreatments", expectedSubTreatments.get(i), actualSubTreatments.get(i));
        }
        assertEquals("Current subtreatment differs", 0, subproduct.getCurrentSubTreatmentIndex());
        assertEquals("Product differs", product, subproduct.getProduct());
        assertEquals("Different subtreatments", treatment.getSubTreatments().get(0), subproduct.getCurrentSubTreatment());
    }
    
    /**
     * Test setId
     */
    @Test
    public void testSetId(){
        subproduct.setId(1337);
        assertEquals("Id not changed", 1337, subproduct.getId());
    }
    
    /**
     * Test setName
     */
    @Test
    public void testSetName(){
        subproduct.setName("NewName");
        assertEquals("Name not changed", "NewName", subproduct.getName());
    }
    
    /**
     * Test setState
     */
    @Test
    public void testSetState(){
        subproduct.setState(State.WASTE);
        assertEquals("State not changed", State.WASTE, subproduct.getState());
    }
    
    /**
     * Test setSubtreatments
     */
//  @Test
//  public void testSetSubtreatments(){
//      subproduct.nextSubTreatment();
//      subproduct.nextSubTreatment();
//      
//      Treatment newTreatment = service.createTreatment("NewTreatment");
//      newTreatment.createSubTreatment("NewSubTreatment", 50, 60, 70);
//      subproduct.setSubtreatments(newTreatment.getSubTreatments());
//      List<SubTreatment> expectedSubTreatments = newTreatment.getSubTreatments();
//      List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
//      for (int i = 0; i < expectedSubTreatments.size(); i++){
//          assertEquals("Subproducts differs", expectedSubTreatments.get(i), actualSubTreatments.get(i));
//      }
//      
//      subproduct.getCurrentSubTreatment();
//  }
    
//  /**
//   * Test addition of subTreatment
//   */
//  @Test
//  public void testAddSubTreatment(){
//      SubTreatment st = new SubTreatment("AddedTreatment", 10, 12, 14);
//      subproduct.addSubTreatment(st);
//      assertEquals("List not enlarged", 4, subproduct.getSubtreatments().size());
//      assertEquals("Last element not the recently added", st, subproduct.getSubtreatments().get(3));
//  }
    
    /**
     * Test product type change
     */
//  @Test
//  public void testSetProduct(){
//      subproduct.nextSubTreatment();
//      subproduct.nextSubTreatment();
//      subproduct.nextSubTreatment();
//      
//      Treatment newTreatment = service.createTreatment("NewTreatment");
//      newTreatment.createSubTreatment("T1", 1, 2, 3);
//      newTreatment.createSubTreatment("T2", 2, 3, 4);
//      Product newProduct = service.createProduct("NewProduct", "Description of new product", newTreatment);
//      subproduct.setProduct(newProduct);
//      assertEquals("Product differs", newProduct, subproduct.getProduct());
//
//      List<SubTreatment> expectedSubTreatments = newTreatment.getSubTreatments();
//      List<SubTreatment> actualSubTreatments = subproduct.getSubtreatments();
//      for (int i = 0; i < expectedSubTreatments.size(); i++){
//          assertEquals("Subproducts differs", expectedSubTreatments.get(i), actualSubTreatments.get(i));
//      }
//      
//      subproduct.getCurrentSubTreatment();
//  }
    
    /**
     * Test nextSubTreatment()
     */
    @Test
    public void testNextSubTreatment(){
        subproduct.nextSubTreatment();
        assertEquals("Iteration error", treatment.getSubTreatments().get(1), subproduct.getCurrentSubTreatment());
        
        subproduct.nextSubTreatment();
        subproduct.nextSubTreatment();
        subproduct.nextSubTreatment();
        assertEquals("Iterated too far", treatment.getSubTreatments().get(2), subproduct.getCurrentSubTreatment());
    }
}
/**
 * @author Martin
 */


package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.SubTreatment;
import carletti.model.Treatment;

public class SubTreatmentTest
{
    private Treatment t;
    private SubTreatment st;

    @Before
    public void setUp() throws Exception
    {
        t = new Treatment("Treatment");
        t.createSubTreatment("subtreatment1", 10, 20, 30);
        st = t.getSubTreatments().get(0);
    }

    @Test
    public void testSubTreatment()
    {
        t = new Treatment("Treatment");
        t.createSubTreatment("Proeve", 40, 50, 60);
        SubTreatment s = t.getSubTreatments().get(0);
        assertEquals("Proeve", s.getName());
        assertEquals(40, s.getDryMin());
        assertEquals(50, s.getDryPrime());
        assertEquals(60, s.getDryMax());
    }

    @Test
    public void testGetDryMin()
    {
        assertEquals(10, st.getDryMin());
        
    }

    @Test
    public void testSetDryMin()
    {
        st.setDryMin(15);
        assertEquals(15, st.getDryMin());
    }

    @Test
    public void testGetDryPrime()
    {
        assertEquals(20, st.getDryPrime());
    }

    @Test
    public void testSetDryPrime()
    {
        st.setDryPrime(25);
        assertEquals(25, st.getDryPrime());
    }

    @Test
    public void testGetDryMax()
    {
        assertEquals(30, st.getDryMax());
    }

    @Test
    public void testSetDryMax()
    {
        st.setDryMax(65);
        assertEquals(65, st.getDryMax());
    }

    @Test
    public void testGetName()
    {
        assertEquals("subtreatment1", st.getName());
    }

    @Test
    public void testSetName()
    {
        st.setName("newName");
        assertEquals("newName", st.getName());
    }

    @Test
    public void testToString()
    {
        String s = "\n" + "subtreatment1" + "\n" + "min= " + "10" + "\nprime= " + "20" + "\nmax= " + "30";
        assertEquals(s, st.toString());
    }
}
/**
 * @author Martin
 */

package carletti.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import carletti.model.Treatment;

public class TreatmentTest
{
    
    private Treatment t1;           

    @Before
    public void setUp() throws Exception
    {
        t1 = new Treatment("treatment1");
    }

    @Test
    public void testTreatment()
    {
        Treatment t2 = new Treatment("treatment2");
        assertEquals("treatment2", t2.getName());
    }

    @Test
    public void testCreateSubTreatment()
    {
        
        t1.createSubTreatment("name", 10, 20, 30);
        assertEquals("name", t1.getSubTreatments().get(0).getName());
        assertEquals(10, t1.getSubTreatments().get(0).getDryMin());
        assertEquals(20, t1.getSubTreatments().get(0).getDryPrime());
        assertEquals(30, t1.getSubTreatments().get(0).getDryMax());
    }

    @Test
    public void testGetName()
    {
        assertEquals("treatment1", t1.getName());
    }

    @Test
    public void testGetSubTreatments()
    {
        t1.createSubTreatment("name", 10, 20, 30);
        assertEquals("name", t1.getSubTreatments().get(0).getName());
        assertEquals(10, t1.getSubTreatments().get(0).getDryMin());
        assertEquals(20, t1.getSubTreatments().get(0).getDryPrime());
        assertEquals(30, t1.getSubTreatments().get(0).getDryMax());
    }

    @Test
    public void testSetName()
    {
        t1.setName("newName");
        assertEquals("newName", t1.getName());
    }

}
package carletti;

public class App {
    public static void main(String[] args){
        LoadGui lg = new LoadGui();
    }
}
/** @author Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 * 
 */
package carletti;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.dao.LocalDao;
import carletti.gui.MainFrame;
import carletti.service.Service;

@SuppressWarnings("serial")
public class LoadGui extends JFrame {

    private Dimension frameSize = new Dimension(300, 200);
    private JPanel selectPanel;

    // private int x = 20, y = 80;

    public LoadGui() {

        this.setTitle("Carletti - Load Screen");
        this.setSize(frameSize);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        selectPanel = new SelectorPanel();
        this.add(selectPanel);

        this.setVisible(true);

    }

    private class SelectorPanel extends JPanel implements ActionListener {
        private JButton btnOk;
        private JRadioButton rbtnLocal, rbtnDao;

        public SelectorPanel() {
            super();
            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            rbtnLocal = new JRadioButton("Local");
            rbtnDao = new JRadioButton("Database");
            btnOk = new JButton("Ok");

            rbtnLocal.addActionListener(this);
            rbtnDao.addActionListener(this);
            btnOk.addActionListener(this);

            layout.setHorizontalGroup(layout
                    .createSequentialGroup()
                    .addGroup(
                            layout.createParallelGroup(
                                    GroupLayout.Alignment.CENTER)
                                    .addComponent(rbtnLocal)
                                    .addComponent(btnOk)).addComponent(rbtnDao));

            layout.setVerticalGroup(layout
                    .createSequentialGroup()
                    .addGroup(
                            layout.createParallelGroup(
                                    GroupLayout.Alignment.CENTER)
                                    .addComponent(rbtnLocal)
                                    .addComponent(rbtnDao))
                    .addComponent(btnOk, 20, 20, 20));
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            Dao dao;
            if (ae.getSource().equals(btnOk)) {
                if (rbtnLocal.isSelected() == true
                        && rbtnDao.isSelected() == false) {
                    dao = LocalDao.getInstance();
                    startProgram(dao);
                } else if (rbtnLocal.isSelected() == false
                        && rbtnDao.isSelected() == true) {
                    dao = JpaDao.getInstance();
                    startProgram(dao);
                } 
            }
            else if(ae.getSource().equals(rbtnLocal)){
                rbtnDao.setSelected(false);
            }
            else if(ae.getSource().equals(rbtnDao)){
                rbtnLocal.setSelected(false);
            }
        }
    }
    
    private void startProgram(Dao dao){
        Service s = Service.getInstance(dao);
        s.createSomeObjects();
        this.setVisible(false);
        MainFrame mf = new MainFrame();
    }

}

---- /Users/tools/Desktop/SourceMerger.java

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Goes through every subfolder in the current directory,
 * finds all the .java files and merges them into a single
 * file.
 * @author Malik Lund
 *
 */
public class SourceMerger {
        private static FileWriter writer;
       
        public static void merge(String path) throws IOException{
                File output = new File("mergedFile.java");
                writer = new FileWriter(output);
                handleDirectory(new File(path));
                writer.close();
        }
       
        private static void handleDirectory(File file) throws IOException{
                if (file.isDirectory()){
                        File[] fileList = file.listFiles();
                        for (File f: fileList){
                                handleDirectory(f);
                        }
                } else {
                        if (file.getAbsolutePath().endsWith(".java")){
                                handleFile(file);
                        }
                }
        }
       
        private static void handleFile(File file) throws IOException{
                Scanner scanner = new Scanner(file);
                writer.write("\n---- " + file.getAbsolutePath() + "\n\n");
                while (scanner.hasNext()){
                        writer.write(scanner.nextLine() + "\n");
                }
                scanner.close();
        }
       
        public static void main(String[] args){
                try {
                        SourceMerger.merge(".");
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}

---- /Users/tools/Desktop/Test.java

public class Test{
	public Test(){
		System.out.println("LOL");  
	}
}

---- /Users/tools/Documents/Datamatiker/1. Semester/Eksamen/SK/Sk_20_juni_2011_Lars_Nielsen_DMU11V/App.java

/**
 * 
 * @author Lars Nielsen - DMU11V
 * @Fag: Internpr¿ve SK 
 * @Date: 20-06-2011 
 * @Class: App
 *
 */
import Service.Service;
import Gui.Gui;

public class App {
	
	public static void main(String[] args){
		
		Service.createSomeObjects();
		Gui g1 = new Gui();
	}

}

---- /Users/tools/Documents/Datamatiker/1. Semester/Eksamen/SK/Sk_20_juni_2011_Lars_Nielsen_DMU11V/Gui/AntalTureOneDialog.java

/**
 * 
 * @author Lars Nielsen - DMU11V
 * @Fag: Internpr¿ve SK 
 * @Date: 20-06-2011 
 * @Class:AntalTureOneDialog
 *
 */
package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Model.Tur;
import Model.Bus;


public class AntalTureOneDialog extends JDialog{
	
	private JButton btnVis, btnHideMe;
	private JTextField txfStartSted, txfSlutSted;
	private Controller btnCtrl = new Controller();
	private JLabel labStart, labSlut;
	private JList list;
	private JScrollPane scroll;
	private Bus bus;
	
	public AntalTureOneDialog(Bus bus){
		this.setModal(true);
		this.setTitle("Opre bus");
		this.setSize(520, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		
		this.bus = bus;
		
		addComp();
		
		this.setVisible(true);
	}
	
	private void addComp(){
		int x = 20;
		int y = 20;
		int btnX = 80;
		int btnY = 20;
		int txfX = 140;
		int txfY = 20;
		int labX = 80;
		int labY = 20;
		int scrollX = 200;
		int scroolY = 200;
		
		labStart = new JLabel("Start");
		labStart.setSize(labX, labY);
		labStart.setLocation(x, y);
		this.add(labStart);
		
		x += 120;
		
		txfStartSted = new JTextField();
		txfStartSted.setSize(txfX, txfY);
		txfStartSted.setLocation(x, y);
		this.add(txfStartSted);
		
		x -= 120;
		y += 40;
		
		labSlut = new JLabel("Slut");
		labSlut.setSize(labX, labY);
		labSlut.setLocation(x, y);
		this.add(labSlut);
		
		x += 120;
		txfSlutSted = new JTextField();
		txfSlutSted.setSize(txfX, txfY);
		txfSlutSted.setLocation(x, y);
		this.add(txfSlutSted);
		
		x -= 120;
		y += 40;
		
		btnVis = new JButton("Vis");
		btnVis.setSize(btnX, btnY);
		btnVis.setLocation(x, y);
		btnVis.addActionListener(btnCtrl);
		this.add(btnVis);
		
		x += 100;
		
		btnHideMe = new JButton("Luk");
		btnHideMe.setSize(btnX, btnY);
		btnHideMe.setLocation(x, y);
		btnHideMe.addActionListener(btnCtrl);
		this.add(btnHideMe);
		
		x = 300;
		y = 20;
		
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scroll = new JScrollPane(list);
		scroll.setSize(scrollX, scroolY);
		scroll.setLocation(x, y);
		this.add(scroll);
	}
	
	private void youCanAbostroftSeMe(){
		this.setVisible(false);
	}
	
	private class Controller implements ActionListener{

		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource().equals(btnVis)){
				ArrayList<Tur> ture = new ArrayList<Tur>();
				String startSted = txfStartSted.getText();
				String slutSted = txfSlutSted.getText();
				
				for(int i = 0; i < bus.getTure().size(); i++){
					String currentStart = bus.getTure().get(i).getStartSted();
					String currentSlut = bus.getTure().get(i).getSlutSted();
					
					if(startSted.compareTo(currentStart) == 0 && slutSted.compareTo(currentSlut) == 0){
						ture.add(bus.getTure().get(i));
					}
					list.setListData(ture.toArray());
				}
			}
			if(ae.getSource().equals(btnHideMe)){
				youCanAbostroftSeMe();
			}
			
		}
		
	}

}

---- /Users/tools/Documents/Datamatiker/1. Semester/Eksamen/SK/Sk_20_juni_2011_Lars_Nielsen_DMU11V/Gui/Gui.java

/**
 * 
 * @author Lars Nielsen - DMU11V
 * @Fag: Internpr¿ve SK 
 * @Date: 20-06-2011 
 * @Class: Gui
 *
 */
package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import Model.Bus;
import Service.Service;

public class Gui extends JFrame{

	private JButton btnOpret, btnUpdate, btnVisTureOne, btnVisAll;
	private JList list;
	private JScrollPane scroll;
	private Controller btnCtrl = new Controller();
	private ArrayList<Bus> busser = Service.getBusser();
	
	public Gui(){
		this.setSize(520, 360);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("DMU11V Eksamen/Pr¿ve Juni 2011");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		addComp();
		
		this.setVisible(true);
	}
	
	private void addComp(){
		int x = 20; 
		int y = 20;
		int btnX = 80;
		int btnY = 20;
		int scrollX = 380;
		int scrollY = 300;
		
		btnOpret = new JButton("Opret");
		btnOpret.setSize(btnX, btnY);
		btnOpret.setLocation(x, y);
		btnOpret.addActionListener(btnCtrl);
		this.add(btnOpret);
		
		y += 40;
		
		btnUpdate = new JButton("Opdater");
		btnUpdate.setSize(btnX, btnY);
		btnUpdate.setLocation(x, y);
		btnUpdate.addActionListener(btnCtrl);
		this.add(btnUpdate);
		
		
		y += 40;
		
		btnVisTureOne = new JButton("Ture a2b");
		btnVisTureOne.setSize(btnX, btnY);
		btnVisTureOne.setLocation(x, y);
		btnVisTureOne.addActionListener(btnCtrl);
		this.add(btnVisTureOne);
		
		y += 40;
		
		btnVisAll = new JButton("Vis alle ture");
		btnVisAll.setSize(btnX, btnY);
		btnVisAll.setLocation(x, y);
		btnVisAll.addActionListener(btnCtrl);
		this.add(btnVisAll);
		
		list = new JList(busser.toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		x = 120;
		y = 20;
		
		scroll = new JScrollPane(list);
		scroll.setSize(scrollX, scrollY);
		scroll.setLocation(x, y);
		this.add(scroll);
	}
	
	private class Controller implements ActionListener{

		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource().equals(btnOpret)){
				OpretDialog opretDia = new OpretDialog();
				list.setListData(Service.getBusser().toArray());
			}
			else if(ae.getSource().equals(btnUpdate)){
				Bus b = (Bus) list.getSelectedValue();
				UpdateDialog update = new UpdateDialog(b);
				list.setListData(Service.getBusser().toArray());
			}
			else if(ae.getSource().equals(btnVisTureOne)){
				Bus bus = (Bus) list.getSelectedValue();
				AntalTureOneDialog antalDia = new AntalTureOneDialog(bus);
			}
			else if(ae.getSource().equals(btnVisAll)){
				Bus bus = (Bus) list.getSelectedValue();
				VisAlleTureDialog altdia = new VisAlleTureDialog(bus);
			}
			
		}
		
	}
}

---- /Users/tools/Documents/Datamatiker/1. Semester/Eksamen/SK/Sk_20_juni_2011_Lars_Nielsen_DMU11V/Gui/OpretDialog.java

/**
 * 
 * @author Lars Nielsen - DMU11V
 * @Fag: Internpr¿ve SK 
 * @Date: 20-06-2011 
 * @Class: OpretDialog
 *
 */
package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Model.Bus;
import Model.BusKategori;
import Service.Service;

public class OpretDialog extends JDialog{
	
	private JButton btnOpret;
	private JTextField txfAntalPassagere, txfIndr;
	private Controller btnCtrl = new Controller();
	private JLabel labAntal, labInr;
	private JList list;
	private JScrollPane scroll;
	
	public OpretDialog(){
		this.setModal(true);
		this.setTitle("Opre bus");
		this.setSize(520, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		
		addComp();
		
		this.setVisible(true);
	}
	
	private void addComp(){
		int x = 20;
		int y = 20;
		int btnX = 80;
		int btnY = 20;
		int txfX = 140;
		int txfY = 20;
		int labX = 80;
		int labY = 20;
		int scrollX = 200;
		int scroolY = 200;
		
		labAntal = new JLabel("Antal pas:");
		labAntal.setSize(labX, labY);
		labAntal.setLocation(x, y);
		this.add(labAntal);
		
		x += 120;
		
		txfAntalPassagere = new JTextField();
		txfAntalPassagere.setSize(txfX, txfY);
		txfAntalPassagere.setLocation(x, y);
		this.add(txfAntalPassagere);
		
		x -= 120;
		y += 40;
		
		labInr = new JLabel("Indreg Œr");
		labInr.setSize(labX, labY);
		labInr.setLocation(x, y);
		this.add(labInr);
		
		x += 120;
		txfIndr = new JTextField();
		txfIndr.setSize(txfX, txfY);
		txfIndr.setLocation(x, y);
		this.add(txfIndr);
		
		x -= 120;
		y += 40;
		
		btnOpret = new JButton("Opret");
		btnOpret.setSize(btnX, btnY);
		btnOpret.setLocation(x, y);
		btnOpret.addActionListener(btnCtrl);
		this.add(btnOpret);
		
		x = 300;
		y = 20;
		
		list = new JList(Service.getKategorier().toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scroll = new JScrollPane(list);
		scroll.setSize(scrollX, scroolY);
		scroll.setLocation(x, y);
		this.add(scroll);
	}
	
	private void youCanAbostroftSeMe(){
		this.setVisible(false);
	}
	
	private class Controller implements ActionListener{

		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource().equals(btnOpret)){
				try{
					int antalPassagerer = Integer.parseInt(txfAntalPassagere.getText());
					int indregistreingsŒr = Integer.parseInt(txfIndr.getText());
					BusKategori kategori = (BusKategori) list.getSelectedValue();
					Bus bus = new Bus(antalPassagerer, indregistreingsŒr, kategori);
					Service.addBus(bus);
					youCanAbostroftSeMe();
				}
				catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "Alle argumenter skal v¾re tal");
				}
			}
			
		}
		
	}

}

---- /Users/tools/Documents/Datamatiker/1. Semester/Eksamen/SK/Sk_20_juni_2011_Lars_Nielsen_DMU11V/Gui/UpdateDialog.java

/**
 * 
 * @author Lars Nielsen - DMU11V
 * @Fag: Internpr¿ve SK 
 * @Date: 20-06-2011 
 * @Class: UpdateDialog
 *
 */
package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Model.Bus;
import Model.BusKategori;

import Service.Service;

public class UpdateDialog extends JDialog{
	
	private JButton btnUpdate;
	private JTextField txfAntalPassagere, txfIndr;
	private Controller btnCtrl = new Controller();
	private JLabel labAntal, labInr;
	private JList list;
	private JScrollPane scroll;
	private Bus bus;
	
	public UpdateDialog(Bus bus){
		this.setModal(true);
		this.setTitle("Opre bus");
		this.setSize(520, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		
		this.bus = bus;
		
		addComp();
		
		this.setVisible(true);
	}
	
	private void addComp(){
		int x = 20;
		int y = 20;
		int btnX = 80;
		int btnY = 20;
		int txfX = 140;
		int txfY = 20;
		int labX = 80;
		int labY = 20;
		int scrollX = 200;
		int scroolY = 200;
		
		labAntal = new JLabel("Antal pas:");
		labAntal.setSize(labX, labY);
		labAntal.setLocation(x, y);
		this.add(labAntal);
		
		x += 120;
		
		txfAntalPassagere = new JTextField();
		txfAntalPassagere.setSize(txfX, txfY);
		txfAntalPassagere.setLocation(x, y);
		this.add(txfAntalPassagere);
		
		x -= 120;
		y += 40;
		
		labInr = new JLabel("Indreg Œr");
		labInr.setSize(labX, labY);
		labInr.setLocation(x, y);
		this.add(labInr);
		
		x += 120;
		txfIndr = new JTextField();
		txfIndr.setSize(txfX, txfY);
		txfIndr.setLocation(x, y);
		this.add(txfIndr);
		
		x -= 120;
		y += 40;
		
		btnUpdate = new JButton("Opdater");
		btnUpdate.setSize(btnX, btnY);
		btnUpdate.setLocation(x, y);
		btnUpdate.addActionListener(btnCtrl);
		this.add(btnUpdate);
		
		x = 300;
		y = 20;
		
		list = new JList(Service.getKategorier().toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scroll = new JScrollPane(list);
		scroll.setSize(scrollX, scroolY);
		scroll.setLocation(x, y);
		this.add(scroll);
	}
	
	private void youCanAbostroftSeMe(){
		this.setVisible(false);
	}
	
	private class Controller implements ActionListener{

		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource().equals(btnUpdate)){
				try{
					int antalPassagerer = Integer.parseInt(txfAntalPassagere.getText());
					int indregistreingsŒr = Integer.parseInt(txfIndr.getText());
					BusKategori kategori = (BusKategori) list.getSelectedValue();
					
					bus.setAntalPassagerer(antalPassagerer);
					bus.setIndregistreingsŒr(indregistreingsŒr);
					bus.setKategori(kategori);
					
					youCanAbostroftSeMe();
				}
				catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "Argument tasted skal v¾re tal");
				}
			}
			
		}
		
	}

}

---- /Users/tools/Documents/Datamatiker/1. Semester/Eksamen/SK/Sk_20_juni_2011_Lars_Nielsen_DMU11V/Gui/VisAlleTureDialog.java

/**
 * 
 * @author Lars Nielsen - DMU11V
 * @Fag: Internpr¿ve SK 
 * @Date: 20-06-2011 
 * @Class: VisAlleTureDialog
 *
 */
package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Model.Bus;
public class VisAlleTureDialog extends JDialog{
	
	private JButton btnHideMe;
	private Controller btnCtrl = new Controller();
	private JList list;
	private JScrollPane scroll;
	private Bus bus;
	
	public VisAlleTureDialog(Bus bus){
		this.setModal(true);
		this.setTitle("Vis alle ture");
		this.setSize(520, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		
		this.bus = bus;
		
		addComp();
		
		this.setVisible(true);
	}
	
	private void addComp(){
		int x = 20;
		int y = 20;
		int btnX = 80;
		int btnY = 20;
		
		int scrollX = 200;
		int scroolY = 200;
					
		btnHideMe = new JButton("Luk");
		btnHideMe.setSize(btnX, btnY);
		btnHideMe.setLocation(x, y);
		btnHideMe.addActionListener(btnCtrl);
		this.add(btnHideMe);
		
		x = 300;
		y = 20;
		
		list = new JList(bus.getTure().toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scroll = new JScrollPane(list);
		scroll.setSize(scrollX, scroolY);
		scroll.setLocation(x, y);
		this.add(scroll);
	}
	
	private void youCanAbostroftSeMe(){
		this.setVisible(false);
	}
	
	private class Controller implements ActionListener{

		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource().equals(btnHideMe)){
				youCanAbostroftSeMe();
			}
			
		}
		
	}

}

---- /Users/tools/Documents/Datamatiker/1. Semester/Eksamen/SK/Sk_20_juni_2011_Lars_Nielsen_DMU11V/Model/Bus.java

/**
 * 
 * @author Lars Nielsen - DMU11V
 * @Fag: Internpr¿ve SK 
 * @Date: 20-06-2011 
 * @Class: Bus
 *
 */
package Model;

import java.util.ArrayList;

public class Bus {

	private int antalPassagerer, indregistreingsŒr;
	private ArrayList<Tur> ture = new ArrayList<Tur>();
	private BusKategori kategori;
	
	public Bus(int antalPassagerer, int indregistreingsŒr, BusKategori kategori) {
		this.antalPassagerer = antalPassagerer;
		this.indregistreingsŒr = indregistreingsŒr;
		this.kategori = kategori;
	}

	public int getAntalPassagerer() {
		return antalPassagerer;
	}

	public void setAntalPassagerer(int antalPassagerer) {
		this.antalPassagerer = antalPassagerer;
	}

	public int getIndregistreingsŒr() {
		return indregistreingsŒr;
	}

	public void setIndregistreingsŒr(int indregistreingsŒr) {
		this.indregistreingsŒr = indregistreingsŒr;
	}
	
	public BusKategori getKategori() {
		return kategori;
	}

	public void setKategori(BusKategori kategori) {
		this.kategori = kategori;
	}

	void addTurUD(Tur tur){
		ture.add(tur);
	}
	
	public void addTur(Tur tur){
		this.addTurUD(tur);
		tur.addBusUD(this);
	}
	
	public ArrayList<Tur> getTure(){
		return ture;
	}
	
	public int getAntalTurer(String startSted, String slutSted){
		int antalture = 0;
		
		for(int i = 0; i < ture.size(); i++){
			String currentStartSted = ture.get(i).getStartSted();
			String currenSlutSted = ture.get(i).getSlutSted();
			
			if(currentStartSted.compareTo(startSted) == 0 && currenSlutSted.compareTo(slutSted) == 0){
				antalture++;
			}
		}
		
		return antalture;
	}
	
	public String toString(){
		return "Kategori: " + getKategori() + " Indregistreings Œr: " + getIndregistreingsŒr() + " Antal passagere: " + getAntalPassagerer();
	}
	
}

---- /Users/tools/Documents/Datamatiker/1. Semester/Eksamen/SK/Sk_20_juni_2011_Lars_Nielsen_DMU11V/Model/BusKategori.java

/**
 * 
 * @author Lars Nielsen - DMU11V
 * @Fag: Internpr¿ve SK 
 * @Date: 20-06-2011 
 * @Class: BusKategori
 *
 */
package Model;

public class BusKategori implements Comparable<BusKategori> {

	private String kategori;
	private int bassisPris, kmPris;
	
	public BusKategori(String kategori, int bassisPris, int kmPris) {
		this.kategori = kategori;
		this.setBassisPris(bassisPris);
		this.kmPris = kmPris;
	}

	public String getKategori() {
		return kategori;
	}
	
	public void setKategori(String kategori) {
		this.kategori = kategori;
	}
	
	public int getBassisPris() {
		return bassisPris;
	}

	public void setBassisPris(int bassisPris) {
		this.bassisPris = bassisPris;
	}

	public int getKmPris() {
		return kmPris;
	}
	
	public void setKmPris(int kmPris) {
		this.kmPris = kmPris;
	}

	public int compareTo(BusKategori kategori) {
		if(this.getKategori().compareTo(kategori.getKategori()) > 0) return 1;
		if(this.getKategori().compareTo(kategori.getKategori()) < 0) return -1;
		return 0;
	}
	
	public String toString(){
		return getKategori();
	}
	
	
}

---- /Users/tools/Documents/Datamatiker/1. Semester/Eksamen/SK/Sk_20_juni_2011_Lars_Nielsen_DMU11V/Model/Dao.java

/**
 * 
 * @author Lars Nielsen - DMU11V
 * @Fag: Internpr¿ve SK 
 * @Date: 20-06-2011 
 * @Class: Dao
 *
 */
package Model;

import java.util.ArrayList;

public class Dao {
	private static ArrayList<Bus> busser = new ArrayList<Bus>();
	private static ArrayList<BusKategori> buskategorier = new ArrayList<BusKategori>();
	private static ArrayList<Tur> ture = new ArrayList<Tur>();
	
	
	public static void addBus(Bus bus){
		busser.add(bus);
	}
	
	public static ArrayList<Bus> getBusser(){
		return new ArrayList<Bus>(busser);
	}
	
	public static void addbusKategori(BusKategori kategori){
		buskategorier.add(kategori);
	}
	
	public static ArrayList<BusKategori> getKategorier(){
		return new ArrayList<BusKategori>(buskategorier);
	}
	
	public static void addTur(Tur tur){
		ture.add(tur);
	}
	
	public static ArrayList<Tur> getTure(){
		return new ArrayList<Tur>(ture);
	}
}

---- /Users/tools/Documents/Datamatiker/1. Semester/Eksamen/SK/Sk_20_juni_2011_Lars_Nielsen_DMU11V/Model/Tur.java

/**
 * 
 * @author Lars Nielsen - DMU11V
 * @Fag: Internpr¿ve SK 
 * @Date: 20-06-2011 
 * @Class: Tur
 *
 */
package Model;
import java.util.ArrayList;

public class Tur {
	
	
	private String startSted, slutSted;
	private int antalPassagerer;
	private int km;
	
	private ArrayList<Bus> busser = new ArrayList<Bus>();
	
	public Tur(String startSted, String slutSted, int antalPassagerer, int km) {
		this.startSted = startSted;
		this.slutSted = slutSted;
		this.antalPassagerer = antalPassagerer;
		this.km = km;
	}
	
	public String getStartSted() {
		return startSted;
	}
	public void setStartSted(String startSted) {
		this.startSted = startSted;
	}
	public String getSlutSted() {
		return slutSted;
	}
	public void setSlutSted(String slutSted) {
		this.slutSted = slutSted;
	}
	public int getAntalPassagerer() {
		return antalPassagerer;
	}
	public void setAntalPassagerer(int antalPassagerer) {
		this.antalPassagerer = antalPassagerer;
	}
	public int getKm() {
		return km;
	}
	public void setKm(int km) {
		this.km = km;
	}
	
	public void addBusUD(Bus bus){
		busser.add(bus);
	}
	
	public void addBus(Bus bus){
		this.addBusUD(bus);
		bus.addTurUD(this);
	}
	
	public ArrayList<Bus> getBusser(){
		return busser;
	}
	
	public double getTurPris(){
		double turpris = 0;
		
		for(int i = 0; i < busser.size(); i++){
			int basisPris = busser.get(i).getKategori().getBassisPris();
			double kmPris = busser.get(i).getKategori().getKmPris() * km;
			
			turpris += basisPris + kmPris;
		}
		
		return turpris;
	}
	
	public String toString(){
		return "Startsted: " + getStartSted() + " Slutsted: " + getSlutSted();
	}
	
	

}

---- /Users/tools/Documents/Datamatiker/1. Semester/Eksamen/SK/Sk_20_juni_2011_Lars_Nielsen_DMU11V/Service/Service.java

/**
 * 
 * @author Lars Nielsen - DMU11V
 * @Fag: Internpr¿ve SK 
 * @Date: 20-06-2011 
 * @Class: Service
 *
 */
package Service;
import java.util.ArrayList;

import Model.Bus;
import Model.BusKategori;
import Model.Tur;
import Model.Dao;

public class Service {

	public static void addBusKategori(BusKategori kategori){
		Dao.addbusKategori(kategori);
	}
	
	public static ArrayList<BusKategori> getKategorier(){
		return Dao.getKategorier();
	}
	
	public static void addBus(Bus bus){
		Dao.addBus(bus);
	}
	
	public static ArrayList<Bus> getBusser(){
		return Dao.getBusser();
	}
	
	public static void addTur(Tur tur){
		Dao.addTur(tur);
	}
	
	public static ArrayList<Tur> getTure(){
		return Dao.getTure();
	}
	
	public static void createSomeObjects(){
		BusKategori bk1 = new BusKategori("Luksus", 2000, 45);
		addBusKategori(bk1);
		BusKategori bk2 = new BusKategori("Standart", 1000, 7);
		addBusKategori(bk2);
		BusKategori bk3 = new BusKategori("Turist", 1500, 20);
		addBusKategori(bk3);
		
		Bus b1 = new Bus(12, 1989, bk1);
		addBus(b1);
		Bus b2 = new Bus(14, 1989, bk2);
		addBus(b2);
		Bus b3 = new Bus(100, 1989, bk3);
		addBus(b3);
		
		Tur t1 = new Tur("Viby", "Rom", 12, 4000);
		Tur t2 = new Tur("Roma", "Viby", 12, 4000);
		
		Tur t3 = new Tur("Bangladesh", "Holland", 7, 2000);
		
		//Grunden til at add pŒ den her mŒde er fordi man skal bruge bin¾rs¿gning
		addTur(t3);
		addTur(t2);
		addTur(t1);
		
		b1.addTur(t1);
		b1.addTur(t3);
		b2.addTur(t3);
		b2.addTur(t2);
		
	}
	
	public static ArrayList<Bus> findBusserTilTur(Tur tur, BusKategori kategori){
		ArrayList<Bus> busser = tur.getBusser();
		ArrayList<Bus> fundetbusser = new ArrayList<Bus>();
		
		for(int i = 0; i < busser.size(); i++){
			if(kategori.compareTo(busser.get(i).getKategori()) == 0){
				fundetbusser.add(busser.get(i));
			}
		}
		
		return fundetbusser;
	}
	
	public static int antalBusser(int minimumKilometer){
		int antalBusser = 0;
		
		ArrayList<Bus> busser = getBusser();
		
		for(int i = 0; i < busser.size(); i++){
			boolean found = false;
			int j = 0;
			Bus b = busser.get(i);
			while(!found && j < b.getTure().size()){
				if(b.getTure().get(j).getKm() <= minimumKilometer){
					found = true;
					antalBusser++;
				}
				else{
					j++;
				}
			}
		}
		return antalBusser;
	}
	
	public static Tur findTur(ArrayList<Tur> ture, String startSted){
		     boolean found = false;
		     int left = 0;
		     int right = ture.size()-1;
		     int mid = 0;
		     
		     Tur tur = null; 

		     while(!found && left <= right){
		          mid = (left+right)/2;
		          if(ture.get(mid).getStartSted().compareTo(startSted) == 0){
		               found = true;
		               tur = ture.get(mid);
		          }
		          else if(ture.get(mid).getStartSted().compareTo(startSted) < 0){
		               left = mid + 1;
		          }
		          else{
		               right = mid -1;
		          }
		      }
		     
		     return tur;
		}

}

---- /Users/tools/Documents/Datamatiker/1. Semester/SK_1/ArrayMetoder.java

public class ArrayM{

    public ArrayM(){


    }

    public int sum(int[] t){

	int sum = 0;
	
	for(int i = 0; i < t.length; i++){

	    s