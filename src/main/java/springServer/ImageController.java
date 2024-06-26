package springServer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class ImageController {

    private static final String URL = "jdbc:sqlite:src/main/resources/usereeg.db";

    @GetMapping("/image")
    public String image(@RequestParam String username, @RequestParam int electrode, Model model) {
        String image = null;
        String sql = "SELECT image FROM user_eeg WHERE username = ? AND electrode_number = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, electrode);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    image = resultSet.getString("image");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        model.addAttribute("username", username);
        model.addAttribute("electrode", electrode);
        model.addAttribute("image", image);
        return "EEG";
    }
}