package debio.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import debio.content.model.Biography;
import debio.content.model.Person;
import util.Config;

/**
 * This class realizes the translation from content model objects to database
 * entries.
 * 
 * @author Benjamin Schuerman <edu (at) schuermann (dot) cc>
 */
public class ModelToDBTransl {

	/* Database connection */
	private Connection con;
	/* Prepared Statement */
	private PreparedStatement stmt;
	/* Database name */
	private String dbName;

	/*
	 * Constructor for database and global variable initialization
	 */
	public ModelToDBTransl() {
		this.con = null;
		this.stmt = null;
		this.dbName = "";

		try {
			Class.forName("org.sqlite.JDBC");
			String relpath = Config.relDBPath;
			this.dbName = Config.dbName;
			this.con = DriverManager.getConnection("jdbc:sqlite:" + relpath + dbName);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getLocalizedMessage());
		}
	}

	/*
	 * Closes the connection of the global variable con
	 */
	public void closeConnection() {
		try {
			if (this.con != null) {
				this.con.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	public ArrayList<Person> getPersons()
	{
		ArrayList<Person> forReturn = new ArrayList<Person>();
		String insertStmt = "select * from Person";
		

		try {
			this.con.setAutoCommit(false);
			stmt = con.prepareStatement(insertStmt);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Person p = new Person();
				p.setId(rs.getString("id"));
				p.setIndex(rs.getString("idx"));
				p.setName(rs.getString("name"));
				p.setAbbr(rs.getString("abbr"));
				p.setDenomination(rs.getString("denom"));
				p.setAdbUrl(rs.getString("adburl"));
				p.setNdbUrl(rs.getString("ndburl"));
				p.setIndexUrl(rs.getString("indexurl"));
				forReturn.add(p);
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}

		return forReturn;
	}

	/*
	 * Selects a person entry identified by a profile ID and returns it as
	 * Person object
	 * 
	 * @param id Profile ID
	 * 
	 * @return Person object
	 */
	public Person selectPerson(String id) {
		String insertStmt = "select * from Person where id = ?";
		Person p = new Person();

		try {
			this.con.setAutoCommit(false);
			stmt = con.prepareStatement(insertStmt);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				p.setId(rs.getString("id"));
				p.setIndex(rs.getString("idx"));
				p.setName(rs.getString("name"));
				p.setAbbr(rs.getString("abbr"));
				p.setDenomination(rs.getString("denom"));
				p.setAdbUrl(rs.getString("adburl"));
				p.setNdbUrl(rs.getString("ndburl"));
				p.setIndexUrl(rs.getString("indexurl"));
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}

		return p;
	}

	/*
	 * Selects a person entry identified by a Person object and returns it as
	 * Person object
	 * 
	 * @param p Person object
	 * 
	 * @return Person object
	 */
	public Person selectPerson(Person p) {
		return this.selectPerson(p.getId());
	}

	/*
	 * Inserts a person entry using a Person object
	 * 
	 * @param p Person object
	 */
	public void insertPerson(Person p) {
		String insertStmt = "insert into Person (id, idx, name, abbr, denom, adburl, ndburl, indexurl)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			this.con.setAutoCommit(false);
			stmt = con.prepareStatement(insertStmt);
			stmt.setString(1, p.getId());
			stmt.setString(2, p.getIndex());
			stmt.setString(3, p.getName());
			stmt.setString(4, p.getAbbr());
			stmt.setString(5, p.getDenomination());
			stmt.setString(6, p.getAdbUrl());
			stmt.setString(7, p.getNdbUrl());
			stmt.setString(8, p.getIndexUrl());
			stmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * Updates a person entry using a profile ID and a Person object
	 * 
	 * @param id Profile ID
	 * 
	 * @param p Person object
	 */
	public void updatePerson(String id, Person p) {
		String insertStmt = "update Person set id = ?, idx = ?, name = ?, abbr = ?, denom = ?, adburl = ?, ndburl = ?, indexurl = ? where id = ?";

		try {
			this.con.setAutoCommit(false);
			stmt = con.prepareStatement(insertStmt);
			stmt.setString(1, p.getId());
			stmt.setString(2, p.getIndex());
			stmt.setString(3, p.getName());
			stmt.setString(4, p.getAbbr());
			stmt.setString(5, p.getDenomination());
			stmt.setString(6, p.getAdbUrl());
			stmt.setString(7, p.getNdbUrl());
			stmt.setString(8, p.getIndexUrl());
			stmt.setString(9, id);
			stmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * Updates a person entry using a Person object
	 * 
	 * @param p Person object
	 */
	public void updatePerson(Person p) {
		this.updatePerson(p.getId(), p);
	}

	/*
	 * Deletes a person entry using a profile ID
	 * 
	 * @param id Profile ID
	 */
	public void deletePerson(String id) {
		String insertStmt = "delete from Person where id = ?";

		try {
			this.con.setAutoCommit(false);
			stmt = con.prepareStatement(insertStmt);
			stmt.setString(1, id);
			stmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * Deletes a person entry using a Person object
	 * 
	 * @param p Person object
	 */
	public void deletePerson(Person p) {
		this.deletePerson(p.getId());
	}

	/*
	 * Selects a biography entry identified by a profile ID and returns it as
	 * Biography object
	 * 
	 * @param id Profile ID
	 * 
	 * @return Biography object
	 */
	public Biography selectBiography(String id) {
		String insertStmt = "select * from Biography where id = ?";
		Biography bio = new Biography();

		try {
			this.con.setAutoCommit(false);
			stmt = con.prepareStatement(insertStmt);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bio.setId(rs.getString("id"));
				bio.setIndex(rs.getString("idx"));
				bio.setAdbCnt(rs.getString("adbcnt"));
				bio.setNdbCnt(rs.getString("ndbcnt"));
				bio.setAdbCntEng(rs.getString("adbcntEng") == null ? "" : rs.getString("adbcntEng"));
				bio.setNdbCntEng(rs.getString("ndbcntEng") == null ? "" : rs.getString("ndbcntEng"));
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}

		return bio;
	}

	/*
	 * Selects a biography entry identified by a Biography object and returns it
	 * as Biography object
	 * 
	 * @param bio Biography object
	 * 
	 * @return Biography object
	 */
	public Biography selectBiography(Biography bio) {
		return this.selectBiography(bio.getId());
	}

	/*
	 * Inserts a biography entry using a Biography object
	 * 
	 * @param bio Biography object
	 */
	public void insertBiography(Biography bio) {
		String insertStmt = "insert into Biography (id, idx, adbcnt, ndbcnt)" + "VALUES (?, ?, ?, ?)";

		try {
			this.con.setAutoCommit(false);
			stmt = con.prepareStatement(insertStmt);
			stmt.setString(1, bio.getId());
			stmt.setString(2, bio.getIndex());
			stmt.setString(3, bio.getAdbCnt());
			stmt.setString(4, bio.getNdbCnt());
			stmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * Updates a biography entry using a profile ID and a Biography object
	 * 
	 * @param id Profile ID
	 * 
	 * @param bio Biography object
	 */
	public void updateBiography(String id, Biography bio) {
		String insertStmt = "update Biography set id = ?, idx = ?, adbcnt = ?, ndbcnt = ?,adbcntEng = ? ,ndbcntEng = ?  where id = ?";

		try {
			this.con.setAutoCommit(false);
			stmt = con.prepareStatement(insertStmt);
			stmt.setString(1, bio.getId());
			stmt.setString(2, bio.getIndex());
			stmt.setString(3, bio.getAdbCnt());
			stmt.setString(4, bio.getNdbCnt());
			stmt.setString(5, bio.getAdbCntEng());
			stmt.setString(6, bio.getNdbCntEng());
			stmt.setString(7, id);

			stmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * Updates a biography entry using a Biography object
	 * 
	 * @param bio Biography object
	 */
	public void updateBiography(Biography bio) {
		this.updateBiography(bio.getId(), bio);
	}

	/*
	 * Deletes a biography entry using a profile ID
	 * 
	 * @param id Profile ID
	 */
	public void deleteBiography(String id) {
		String insertStmt = "delete from Biography where id = ?";

		try {
			this.con.setAutoCommit(false);
			stmt = con.prepareStatement(insertStmt);
			stmt.setString(1, id);
			stmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.err.println(e.getLocalizedMessage());
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * Deletes a biography entry using a Biography object
	 * 
	 * @param bio Biography object
	 */
	public void deleteBiography(Biography bio) {
		this.deleteBiography(bio.getId());
	}

}
