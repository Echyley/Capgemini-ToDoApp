package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

public class TaskController {

    public void save(Task task) {

        String sql = "INSERT INTO tasks (idProject, name, description, notes, completed,deadline, createdDate, updatedDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getCompleted());
            statement.setString(5, task.getNotes());
            // Atenção, o Date abaixo é do pacote de SQL e não do java util!
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedDate().getTime()));
            statement.setDate(8, new Date(task.getUpdatedDate().getTime()));
            statement.execute();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar a tarefa :(" + ex.getMessage(), ex);
            // O bloco finally sempre será executado! Não se esqueça de fechar a connection
            // e o statement quando forem abertos!
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void update(Task task) {

        String sql = "UPDATE taks SET idProject = ?, name = ?, description = ?, notes = ?, completed = ?,deadline = ?, createdDate = ?, updatedDate = ?, WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Estabelecendo a conexão com o banco de dados:
            connection = ConnectionFactory.getConnection();
            // Preparando Query:
            statement = connection.prepareStatement(sql);

            // Setando os valores do statement:
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getIdName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.getCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedDate().getTime()));
            statement.setDate(8, new Date(task.getUpdatedDate().getTime()));
            statement.setInt(9, task.getId());
            // executando a query:
            statement.execute();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa :(" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void removeById(int taskId) {

        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // id será alterado pelo usuário a partir do statement abaixo;
            // Estabelecendo a conexão com o banco de dados:
            connection = ConnectionFactory.getConnection();
            // Preparando Query:
            statement = connection.prepareStatement(sql);
            // Setando valores:
            statement.setInt(1, taskId);
            // Executando Query:
            statement.execute();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar a tarefa :(");
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Task> getAll(int idProject) {

        String sql = "SELECT + FROM task WHERE idProject =?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        // Lista de tarefas que será desenvolvida quando a chamada do método acontecer
        List<Task> tasks = new ArrayList<Task>();

        try {
            // Criação da conexão:
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            // Setando valores que correspondem ao filtro de busca:
            statement.setInt(1, idProject);
            // Valor retornado pela execução da Query:
            resultSet = statement.executeQuery();
            // Enquanto houverem valores a serem percorridos no resultSet:
            while (resultSet.next()) {

                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedDate(resultSet.getDate("createdDate"));
                task.setUpdatedDate(resultSet.getDate("updatedDate"));
                task.add(task);

            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a tarefa :(");
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        // Lista de tarefas criada e carregada no banco de dados!
        return tasks;
    }

}
