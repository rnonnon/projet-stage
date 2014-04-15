import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.joda.time.DateTime;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.stage.dao.CompanyDAO;
import fr.stage.dao.ComputerDAO;
import fr.stage.dao.ConnectionManager;
import fr.stage.domain.Company;
import fr.stage.domain.Computer;

public class mainClasse {

    public static void main(String[] args) {

	byte errorCode = 32;
	if ((errorCode & 0x01) == 0x01) {
	    System.out.println("0x01");
	}
	if ((errorCode & 0x02) == 0x02) {
	    System.out.println("0x02");
	}
	if ((errorCode & 0x04) == 0x04) {
	    System.out.println("0x04");
	}
	if ((errorCode & 0x08) == 0x08) {
	    System.out.println("0x08");
	}
	if ((errorCode & 0x10) == 0x10) {
	    System.out.println("0x10");
	}
	if ((errorCode & 0x20) == 0x20) {
	    System.out.println("0x20");
	}

	// TODO Auto-generated method stub
	ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
		"beans.xml");

	ConnectionManager coMan = applicationContext.getBean(ConnectionManager.class);
	ComputerDAO computerDAO = applicationContext.getBean(ComputerDAO.class);
	CompanyDAO companyDAO = applicationContext.getBean(CompanyDAO.class);

	// IConnectionManager coMan = ConnectionManager.getInstance();
	Connection co = coMan.getConnection();
	try {
	    ResultSet res;
	    Statement stm = co.createStatement();
	    res = stm.executeQuery("SELECT * FROM company");
	    while (res.next()) {
		System.out.println(res.getString("name"));
	    }

	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Computer computer = new Computer();
	Company comp = new Company();
	comp.setName("totoCompany");

	computer.setName("TOTO_COMPUTER5");
	// computer.setIntroducedDate(new Date());
	computer.setDiscontinuedDate(new DateTime());
	// computer.setCompany(comp);

	// ComputerDAO computerDAO = FactoryDAO.getComputerDAOInstance();
	// CompanyDAO companyDAO = FactoryDAO.getCompanyDAOInstance();

	// try {
	// companyDAO.create(comp);
	// computerDAO.create(computer);
	// System.out.println(computer.getId());
	// Computer compFind = new Computer();
	// compFind.setId(1);
	// computerDAO.find(compFind);
	// System.out.println(compFind);
	// Computer computerFind2 = computerDAO.find(2);
	// System.out.println(computerFind2);
	// computerDAO.delete(616);
	// List<Computer> computers = computerDAO.findList(ComputerDAO
	// .findAllParameters());
	// for (Computer c : computers) {
	// System.out.println(c);
	// }
	// // CompanyDAO.getInstance().delete(company);
	// }
	// catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
    }
}
