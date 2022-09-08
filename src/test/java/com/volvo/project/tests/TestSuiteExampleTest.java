package com.volvo.project.tests;

import com.volvo.project.base.TestBase;
import com.volvo.project.components.database.ConnectionToDB;
import com.volvo.project.components.datatdriventesting.ExcelLibrary;
import com.volvo.project.components.openshift.SearchInfoInLogs;
import com.volvo.project.components.fileoperations.ExcelFileOperation;
import com.volvo.project.components.fileoperations.ExcelFileWriteOperation;
import com.volvo.project.components.outlook.CheckingMails;
import com.volvo.project.components.outlook.OutlookEmail;
import com.volvo.project.components.outlook.OutlookEmail1;
import com.volvo.project.enums.DataBaseSelection;
import com.volvo.project.enums.PathToSystemLogs;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class TestSuiteExampleTest extends TestBase {

   /* @BeforeClass
    public static void prepareDataBase() throws ClassNotFoundException, SQLException, IOException {
        logger.info("starting sql injection");
        DataSource dataSource = prepareDataSource();
        SqlUtils sqlUtils = new SqlUtils();
        final String TESTDATA_DELETE_SQL = "./src/test/resources/ExampleOfSqlScriptYouWantToRun.sql";
        sqlUtils.executeSqls(dataSource, new String[] { TESTDATA_DELETE_SQL });
        logger.info("sql script injection finished");
    }

    public static DataSource prepareDataSource() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@" + PageObject.INTERNET_DB;
        String user = PageObject.INTERNET_DB_INTERNETDBA_USER;
        String pwd = PageObject.INTERNET_DB_INTERNETDBA_PASS;
        final Connection connection = DriverManager.getConnection(url, user, pwd);
        DataSource dataSource = new MockedDataSource(connection);
        return dataSource;
    }*/
        //private CompareImages compImg = new CompareImages()

/*//    @Test(groups = {"MQintegration","smoke"})
    @Xray(test = "INTERNET-1", labels = "Label1")
    public void sendingMessagesViaMQExplorer() throws IOException, ParseException {
        //when: "sending the Test message via MQ"
        new ConnectMQ();
        String messagePath = "incoming_xml_messages/deleteDealerNewBRANDBAfield.txt";
        ConnectMQ.sendMQ(ManagerName.GimliA1, QueueName.DealerCTDIQueue, messagePath);
        //then:
        ConnectMQ.receiveMQ(ManagerName.GimliA1, QueueName.DealerCTDIQueue);
        SearchInfoInLogs searchInfoInLogs = new SearchInfoInLogs();
        String a1 = "CTDIGatewayDealerRequestProcessor";
        searchInfoInLogs.searchingInformationInLogs(a1);
    }*/


//    @Test(groups = {"database","regression"})
    public void connectingToDataBase() throws SQLException {
        // when: "connecting with DB"
        ConnectionToDB connectionToDB = new ConnectionToDB(DataBaseSelection.DataBaseNumber1);
        connectionToDB.connect();
        ResultSet rs = connectionToDB.executeQuery("select * from adm_user where id = 'A049473'");
        String userID = "";
        String userName = "";
        String userSurname = "";
        while (rs.next()) {
            userID = rs.getString(1);
            userName = rs.getString(2);
            userSurname = rs.getString(3);
//            float price = rs.getFloat(3);
//            int sales = rs.getInt(4);
//            int total = rs.getInt(5);
        }
        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(userID).isEqualTo("A049473").describedAs("incorrect userID");
        softly.assertThat(userName).isEqualTo("Paweł").describedAs("incorrect username");
        softly.assertThat(userSurname).isEqualTo("Piesniewski").describedAs("incorrect usersurname");
        softly.assertAll();
    }

    @Test(groups = {"searchInfo","smoke"})
    public void searchingInformationInLogs() throws IOException, ParseException {

        SearchInfoInLogs searchInfoInLogs = new SearchInfoInLogs();
        searchInfoInLogs.searchingInformationInLogs(PathToSystemLogs.SYSTEM_LOGS_QA.toString(),"c.v.b.u.b.b.", startTimestamp.get());
    }

    @Test(groups = {"Excel File Operation","Regression"})
    public void readExcelFile() throws IOException, ParseException {
        ExcelFileOperation objExcelFile = new ExcelFileOperation();
        //Prepare the path of excel file
        //String filePath = System.getProperty("user.dir")+"\\src\\excelExportAndFileIO";
        String filePath = "C:\\Java Framework";
        objExcelFile.readExcel(filePath, "ExportProductCost.xlsx", "Sheet_0");
    }
    @Test(groups = {"Excel Write File Operation-By New Row","Regression"})
    public void WriteExcelFile_ByNewRow() throws IOException {
        String[] valueToWrite = {"PDC"};
        ExcelFileOperation objExcelFile = new ExcelFileOperation();
        //Prepare the path of excel file
        //String filePath = System.getProperty("user.dir")+"\\src\\excelExportAndFileIO";
        String filePath = "C:\\Java Framework\\ExportProductCost.xlsx";
        objExcelFile.writeExcel_ByNewRow("C:\\Java Framework\\ExportProductCost.xlsx","Sheet_0",valueToWrite);
    }
    @Test(groups = {"Excel Write File Operation-By Existing Row","Regression"})
    public void WriteExcelFile_ByExistingRow() throws Exception {
        String[] valueToWrite = {"PDC"};
        //Prepare the path of excel file
        //String filePath = System.getProperty("user.dir")+"\\src\\excelExportAndFileIO";
        String filePath = "C:\\Java Framework\\ExportProductCost.xlsx";
        ExcelFileWriteOperation objExcelFile = new ExcelFileWriteOperation(filePath);
        objExcelFile.updateExistingRow("C:\\Java Framework\\ExportProductCost.xlsx","Sheet_0",valueToWrite);
    }
    @Test(groups = {"Excel Write File Operation By Column Name","Regression"})
    public void WriteExcelFile_ByColumnName() throws Exception {
        //Prepare the path of excel file
        //String filePath = System.getProperty("user.dir")+"\\src\\excelExportAndFileIO";
        String filePath = "C:\\Java Framework\\ExportProductCost.xlsx";
        //String filePath = "C:\\Java Framework\\SPIDR_ProductExport_024a5755-dc60-46a7-95c1-0a79de961d82.xlsx";
        //String filePath = "C:\\Java Framework\\testOpsJavaFramework\\target\\test-output\\SPIDR_ProductExport_2a0308dd-dd5b-4bed-8133-5703f57910dc.xlsx";
        ExcelFileWriteOperation objExcelFile = new ExcelFileWriteOperation(filePath);

        objExcelFile.setCellData("Sheet_0","Region",2,"Northeast");
        objExcelFile.setCellData("Sheet_0","Brand",2,"Volvo");
        objExcelFile.setCellData("Sheet_0","Region",4,"Northeast");
        objExcelFile.setCellData("Sheet_0","Brand",4,"Volvo");
        objExcelFile.setCellData("Sheet_0","Region",6,"Southwest");
        objExcelFile.setCellData("Sheet_0","Brand",6,"Volvo");
        /*objExcelFile.setCellData("Sheet_0","Program Number",2,"SMA304117\n");
        //objExcelFile.setCellData("Sheet_0","Region",3,"Southwest");
        objExcelFile.setCellData("Sheet_0","Program Number",3,"SMA304117\n");
        objExcelFile.setCellData("Sheet_0","Region",4,"Northeast");
        objExcelFile.setCellData("Sheet_0","Program Number",4,"SMA304117\n");
        objExcelFile.setCellData("Sheet_0","Region",5,"Southwest");
        objExcelFile.setCellData("Sheet_0","Program Name",5,"Emergency");
        objExcelFile.setCellData("Sheet_0","Region",6,"Southwest");
        objExcelFile.setCellData("Sheet_0","Program Name",6,"Emergency");

         */
    }
    @Test(groups = {"Excel Write File Operation By Column Name","Regression"})
    public void WriteExcelFile_ByColumnName1() throws Exception {
        //Prepare the path of excel file
        //String filePath = System.getProperty("user.dir")+"\\src\\excelExportAndFileIO";
        String filePath = "C:\\Java Framework\\ExportProductCost.xlsx";
        //String filePath = "C:\\Java Framework\\SPIDR_ProductExport_024a5755-dc60-46a7-95c1-0a79de961d82.xlsx";
        //String filePath = "C:\\Java Framework\\testOpsJavaFramework\\target\\test-output\\SPIDR_ProductExport_2a0308dd-dd5b-4bed-8133-5703f57910dc.xlsx";
        ExcelFileWriteOperation objExcelFile = new ExcelFileWriteOperation(filePath);

        objExcelFile.updateExistingCellData("Sheet_0","Region",2,"Northeast");
        objExcelFile.updateExistingCellData("Sheet_0","Brand",2,"Volvo");
        objExcelFile.updateExistingCellData("Sheet_0","Region",4,"Northeast");
        objExcelFile.updateExistingCellData("Sheet_0","Brand",4,"Volvo");
        objExcelFile.updateExistingCellData("Sheet_0","Region",6,"Southwest");
        objExcelFile.updateExistingCellData("Sheet_0","Brand",6,"Volvo");
        
    }

    @Test(groups = {"Excel Write File Operation By Column Name","Regression"})
    public void WriteExcelFile_ByColumnName4() throws Exception {
        //Prepare the path of excel file
        //String filePath = System.getProperty("user.dir")+"\\src\\excelExportAndFileIO";
        String filePath = "C:\\Java Framework\\ExportProductCost.xlsx";
        //String filePath = "C:\\Java Framework\\SPIDR_ProductExport_024a5755-dc60-46a7-95c1-0a79de961d82.xlsx";
        //String filePath = "C:\\Java Framework\\testOpsJavaFramework\\target\\test-output\\SPIDR_ProductExport_2a0308dd-dd5b-4bed-8133-5703f57910dc.xlsx";
        ExcelLibrary objExcelFile = new ExcelLibrary();

        objExcelFile.writeToExcel(filePath,2,4,"MCB37190\n");
        objExcelFile.writeToExcel(filePath,2,2,"Northeast");
        objExcelFile.writeToExcel(filePath,4,2,"Southwest");
        objExcelFile.writeToExcel(filePath,6,2,"Northeast");
        objExcelFile.writeToExcel(filePath,8,2,"Southwest");
        objExcelFile.writeToExcel(filePath,2,3,"Volvo");
        objExcelFile.writeToExcel(filePath,4,3,"Mack");
        objExcelFile.writeToExcel(filePath,6,3,"Both");
        objExcelFile.writeToExcel(filePath,8,3,"Mack");
    }






    @Test(groups = {"Checking Mails","Regression"})
    public void CheckingMails() throws IOException {
        CheckingMails objCheckingmail = new CheckingMails();
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "kumarfeb29@gmail.com";// change accordingly
        String password = "Prapti@3793801";// change accordingly
        objCheckingmail.check(host, mailStoreType, username, password);
    }

    @Test(groups = {"Checking Mails","Regression"})
    public void CheckingOutlookMails() throws Exception {
        OutlookEmail objEmail = new OutlookEmail();
        String username = "ashwini.kumar.s@consultant.volvo.com";// change accordingly
        String password = "Prapti@379";// change accordingly
        objEmail.checkEmails(username, password);
    }

    @Test(groups = {"Checking Mails","Regression"})
    public void CheckingOutlookMails1() throws Exception {
        OutlookEmail1 objEmail = new OutlookEmail1();
        String username = "ashwini.kumar.s@consultant.volvo.com";// change accordingly
        String password = "Prapti@379";// change accordingly
        objEmail.checkEmails(username, password);
    }
}
