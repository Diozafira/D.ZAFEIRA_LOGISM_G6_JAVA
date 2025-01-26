import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


// Κλάση Book για να αναπαριστά τα δεδομένα κάθε βιβλίου


public class MyJDBC {

    public static void main(String[] args) {
        // Δηλώνουμε τις πληροφορίες για τη σύνδεση με τη βάση δεδομένων
        String url = "jdbc:mysql://localhost:3306/getdownwithflu";  // Αντικαταστήστε με τη διεύθυνση της βάσης σας
        String user = "root";  // Αντικαταστήστε με το όνομα χρήστη της βάσης σας
        String password = "Govo1986";  // Αντικαταστήστε με τον κωδικό πρόσβασής σας

        // Δηλώνονται οι αντικείμενα που χρησιμοποιούνται για τη σύνδεση με τη βάση δεδομένων
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Σύνδεση με τη βάση δεδομένων
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // Ερώτημα SQL για να πάρουμε όλα τα βιβλία από τον πίνακα books
            String query = "SELECT * FROM diseases";

            // Εκτέλεση του query και αποθήκευση των αποτελεσμάτων στο resultSet
            resultSet = statement.executeQuery(query);

        } catch (Exception e) {
            // Αν υπάρχει σφάλμα κατά τη σύνδεση ή εκτέλεση του query, το εκτυπώνουμε
            e.printStackTrace();
        } finally {
            // Κλείσιμο των αντικειμένων που χρησιμοποιήθηκαν για τη σύνδεση με τη βάση δεδομένων
            try {
                if (resultSet != null) resultSet.close();  // Κλείσιμο του ResultSet
                if (statement != null) statement.close();  // Κλείσιμο του Statement
                if (connection != null) connection.close(); // Κλείσιμο της σύνδεσης
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}