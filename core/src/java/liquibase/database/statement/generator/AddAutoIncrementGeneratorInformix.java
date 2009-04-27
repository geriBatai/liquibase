package liquibase.database.statement.generator;

import liquibase.database.statement.AddAutoIncrementStatement;
import liquibase.database.statement.syntax.Sql;
import liquibase.database.statement.syntax.UnparsedSql;
import liquibase.database.Database;
import liquibase.database.InformixDatabase;
import liquibase.exception.LiquibaseException;
import liquibase.exception.StatementNotSupportedOnDatabaseException;
import liquibase.exception.JDBCException;

public class AddAutoIncrementGeneratorInformix extends AddAutoIncrementGenerator {
    public int getSpecializationLevel() {
        return SPECIALIZATION_LEVEL_DATABASE_SPECIFIC;
    }

    public boolean isValidGenerator(AddAutoIncrementStatement statement, Database database) {
        return database instanceof InformixDatabase;
    }

    @Override
    public GeneratorValidationErrors validate(AddAutoIncrementStatement addAutoIncrementStatement, Database database) {
        GeneratorValidationErrors validationErrors = super.validate(addAutoIncrementStatement, database);

        validationErrors.checkRequiredField("columnDataType", addAutoIncrementStatement.getColumnDataType());

        return validationErrors;
    }

    public Sql[] generateSql(AddAutoIncrementStatement statement, Database database) throws JDBCException {
        return new Sql[]{new UnparsedSql("ALTER TABLE " + database.escapeTableName(statement.getSchemaName(), statement.getTableName()) + " MODIFY " + database.escapeColumnName(statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " " + database.getColumnType(statement.getColumnDataType(), true))};
    }
}
