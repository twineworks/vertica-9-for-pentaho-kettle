/*! ******************************************************************************
 *
 * Vertica 9 database metadata for Pentaho kettle
 *
 * Copyright (C) 2017 by Twineworks GmbH http://twineworks.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package com.twineworks.kettle.vertica9.database;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.BaseDatabaseMeta;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseInterface;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.plugins.DatabaseMetaPlugin;
import org.pentaho.di.core.row.ValueMetaInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

@DatabaseMetaPlugin(type = "VERTICA9", typeDescription = "Vertica 9+")
public class Vertica9DatabaseMeta extends BaseDatabaseMeta implements DatabaseInterface {

  @Override
  public int[] getAccessTypeList() {
    return new int[]{
        DatabaseMeta.TYPE_ACCESS_NATIVE,
        DatabaseMeta.TYPE_ACCESS_ODBC,
        DatabaseMeta.TYPE_ACCESS_JNDI
    };
  }

  @Override
  public String getDriverClass() {
    if (getAccessType() == DatabaseMeta.TYPE_ACCESS_NATIVE) {
      return "com.vertica.jdbc.Driver";
    } else {
      return "sun.jdbc.odbc.JdbcOdbcDriver"; // always ODBC!
    }

  }

  @Override
  public String getURL(String hostname, String port, String databaseName) {
    if (getAccessType() == DatabaseMeta.TYPE_ACCESS_NATIVE) {
      return "jdbc:vertica://" + hostname + ":" + port + "/" + databaseName;
    } else {
      return "jdbc:odbc:" + databaseName;
    }
  }


  @Override
  public boolean isFetchSizeSupported() {
    return false;
  }


  @Override
  public boolean supportsBitmapIndex() {
    return false;
  }


  @Override
  public String getAddColumnStatement(String tablename, ValueMetaInterface v, String tk, boolean use_autoinc,
                                      String pk, boolean semicolon) {
    return "ALTER TABLE " + tablename + " ADD " + getFieldDefinition(v, tk, pk, use_autoinc, true, false);
  }


  @Override
  public String getModifyColumnStatement(String tablename, ValueMetaInterface v, String tk, boolean use_autoinc,
                                         String pk, boolean semicolon) {
    return "ALTER TABLE "
        + tablename + " ALTER COLUMN "
        + v.getName() + " SET DATA TYPE " + getFieldDefinition(v, tk, pk, use_autoinc, false, false);
  }

  @Override
  public String getFieldDefinition(ValueMetaInterface v, String tk, String pk, boolean use_autoinc,
                                   boolean add_fieldname, boolean add_cr) {
    String retval = "";

    String fieldname = v.getName();
    int length = v.getLength();

    if (add_fieldname) {
      retval += fieldname + " ";
    }

    int type = v.getType();
    switch (type) {
      case ValueMetaInterface.TYPE_DATE:
      case ValueMetaInterface.TYPE_TIMESTAMP:
        retval += "TIMESTAMP";
        break;
      case ValueMetaInterface.TYPE_BOOLEAN:
        retval += "BOOLEAN";
        break;
      case ValueMetaInterface.TYPE_NUMBER:
      case ValueMetaInterface.TYPE_BIGNUMBER:
        retval += "FLOAT";
        break;
      case ValueMetaInterface.TYPE_INTEGER:
        retval += "INTEGER";
        break;
      case ValueMetaInterface.TYPE_STRING:
        retval += (length < 1) ? "VARCHAR" : "VARCHAR(" + length + ")";
        break;
      case ValueMetaInterface.TYPE_BINARY:
        retval += (length < 1) ? "VARBINARY" : "VARBINARY(" + length + ")";
        break;
      default:
        retval += " UNKNOWN";
        break;
    }

    if (add_cr) {
      retval += Const.CR;
    }

    return retval;
  }

  @Override
  public String[] getUsedLibraries() {
    return new String[]{};
  }

  @Override
  public int getDefaultDatabasePort() {
    return 5433;
  }

  @Override
  public String getLimitClause(int nrRows) {
    return " LIMIT " + nrRows;
  }

  @Override
  public int getMaxVARCHARLength() {
    return 65000;
  }

  @Override
  public String[] getReservedWords() {
    return new String[]{
        // retrieved using  'select * from keywords;'
        "ABORT",
        "ABSOLUTE",
        "ACCESS",
        "ACCESSRANK",
        "ACCOUNT",
        "ACTION",
        "ACTIVATE",
        "ADD",
        "ADMIN",
        "AFTER",
        "AGGREGATE",
        "ALL",
        "ALSO",
        "ALTER",
        "ANALYSE",
        "ANALYTIC",
        "ANALYZE",
        "AND",
        "ANTI",
        "ANY",
        "ARRAY",
        "AS",
        "ASC",
        "ASSERTION",
        "ASSIGNMENT",
        "AT",
        "AUTHENTICATION",
        "AUTHORIZATION",
        "AUTO",
        "AUTO_INCREMENT",
        "AVAILABLE",
        "BACKWARD",
        "BASENAME",
        "BATCH",
        "BEFORE",
        "BEGIN",
        "BEST",
        "BETWEEN",
        "BIGINT",
        "BINARY",
        "BIT",
        "BLOCK",
        "BLOCK_DICT",
        "BLOCKDICT_COMP",
        "BOOLEAN",
        "BOTH",
        "BROADCAST",
        "BY",
        "BYTEA",
        "BYTES",
        "BZIP",
        "BZIP_COMP",
        "CACHE",
        "CALLED",
        "CASCADE",
        "CASE",
        "CAST",
        "CATALOGPATH",
        "CHAIN",
        "CHAR",
        "CHAR_LENGTH",
        "CHARACTER",
        "CHARACTER_LENGTH",
        "CHARACTERISTICS",
        "CHARACTERS",
        "CHECK",
        "CHECKPOINT",
        "CLASS",
        "CLEAR",
        "CLOSE",
        "CLUSTER",
        "COLLATE",
        "COLSIZES",
        "COLUMN",
        "COLUMNS_COUNT",
        "COMMENT",
        "COMMIT",
        "COMMITTED",
        "COMMONDELTA_COMP",
        "COMMUNAL",
        "COMPLEX",
        "CONNECT",
        "CONSTRAINT",
        "CONSTRAINTS",
        "CONTROL",
        "COPY",
        "CORRELATION",
        "CPUAFFINITYMODE",
        "CPUAFFINITYSET",
        "CREATE",
        "CREATEDB",
        "CREATEUSER",
        "CROSS",
        "CSV",
        "CUBE",
        "CURRENT",
        "CURRENT_DATABASE",
        "CURRENT_DATE",
        "CURRENT_SCHEMA",
        "CURRENT_TIME",
        "CURRENT_TIMESTAMP",
        "CURRENT_USER",
        "CURSOR",
        "CUSTOM",
        "CYCLE",
        "DATA",
        "DATABASE",
        "DATAPATH",
        "DATEDIFF",
        "DATETIME",
        "DAY",
        "DEACTIVATE",
        "DEALLOCATE",
        "DEC",
        "DECIMAL",
        "DECLARE",
        "DECODE",
        "DEFAULT",
        "DEFAULTS",
        "DEFERRABLE",
        "DEFERRED",
        "DEFINE",
        "DEFINER",
        "DELETE",
        "DELIMITER",
        "DELIMITERS",
        "DELTARANGE_COMP",
        "DELTARANGE_COMP_SP",
        "DELTAVAL",
        "DEPENDS",
        "DESC",
        "DETERMINES",
        "DIRECT",
        "DIRECTCOLS",
        "DIRECTED",
        "DIRECTGROUPED",
        "DIRECTPROJ",
        "DISABLE",
        "DISABLED",
        "DISCONNECT",
        "DISTINCT",
        "DISTVALINDEX",
        "DO",
        "DOMAIN",
        "DOUBLE",
        "DROP",
        "DURABLE",
        "EACH",
        "ELSE",
        "ENABLE",
        "ENABLED",
        "ENCLOSED",
        "ENCODED",
        "ENCODING",
        "ENCRYPTED",
        "END",
        "ENFORCELENGTH",
        "EPHEMERAL",
        "EPOCH",
        "ERROR",
        "ESCAPE",
        "EVENT",
        "EVENTS",
        "EXCEPT",
        "EXCEPTION",
        "EXCEPTIONS",
        "EXCLUDE",
        "EXCLUDING",
        "EXCLUSIVE",
        "EXECUTE",
        "EXECUTIONPARALLELISM",
        "EXISTS",
        "EXPIRE",
        "EXPLAIN",
        "EXPORT",
        "EXTERNAL",
        "EXTRACT",
        "FAILED_LOGIN_ATTEMPTS",
        "FALSE",
        "FAULT",
        "FENCED",
        "FETCH",
        "FILESYSTEM",
        "FILLER",
        "FILTER",
        "FIRST",
        "FIXEDWIDTH",
        "FLEX",
        "FLEXIBLE",
        "FLOAT",
        "FOLLOWING",
        "FOR",
        "FORCE",
        "FOREIGN",
        "FORMAT",
        "FORWARD",
        "FREEZE",
        "FROM",
        "FULL",
        "FUNCTION",
        "FUNCTIONS",
        "GCDDELTA",
        "GET",
        "GLOBAL",
        "GRACEPERIOD",
        "GRANT",
        "GROUP",
        "GROUPED",
        "GROUPING",
        "GZIP",
        "GZIP_COMP",
        "HANDLER",
        "HAVING",
        "HCATALOG",
        "HCATALOG_CONNECTION_TIMEOUT",
        "HCATALOG_DB",
        "HCATALOG_SCHEMA",
        "HCATALOG_SLOW_TRANSFER_LIMIT",
        "HCATALOG_SLOW_TRANSFER_TIME",
        "HCATALOG_USER",
        "HIGH",
        "HIVE_PARTITION_COLS",
        "HIVESERVER2_HOSTNAME",
        "HOLD",
        "HOST",
        "HOSTNAME",
        "HOUR",
        "HOURS",
        "IDENTIFIED",
        "IDENTITY",
        "IDLESESSIONTIMEOUT",
        "IF",
        "IGNORE",
        "ILIKE",
        "ILIKEB",
        "IMMEDIATE",
        "IMMUTABLE",
        "IMPLICIT",
        "IN",
        "INCLUDE",
        "INCLUDING",
        "INCREMENT",
        "INDEX",
        "INHERITS",
        "INITIALLY",
        "INNER",
        "INOUT",
        "INPUT",
        "INSENSITIVE",
        "INSERT",
        "INSTEAD",
        "INT",
        "INTEGER",
        "INTERFACE",
        "INTERPOLATE",
        "INTERSECT",
        "INTERVAL",
        "INTERVALYM",
        "INTO",
        "INVOKER",
        "IS",
        "ISNULL",
        "ISOLATION",
        "JOIN",
        "JSON",
        "KEY",
        "KSAFE",
        "LABEL",
        "LANCOMPILER",
        "LANGUAGE",
        "LARGE",
        "LAST",
        "LATEST",
        "LEADING",
        "LEFT",
        "LESS",
        "LEVEL",
        "LIBRARY",
        "LIKE",
        "LIKEB",
        "LIMIT",
        "LISTEN",
        "LOAD",
        "LOCAL",
        "LOCALTIME",
        "LOCALTIMESTAMP",
        "LOCATION",
        "LOCK",
        "LONG",
        "LOW",
        "LZO",
        "MANAGED",
        "MASK",
        "MATCH",
        "MATCHED",
        "MATERIALIZE",
        "MAXCONCURRENCY",
        "MAXCONCURRENCYGRACE",
        "MAXCONNECTIONS",
        "MAXMEMORYSIZE",
        "MAXPAYLOAD",
        "MAXVALUE",
        "MEDIUM",
        "MEMORYCAP",
        "MEMORYSIZE",
        "MERGE",
        "MERGEOUT",
        "METHOD",
        "MICROSECONDS",
        "MILLISECONDS",
        "MINUS",
        "MINUTE",
        "MINUTES",
        "MINVALUE",
        "MODE",
        "MODEL",
        "MONEY",
        "MONTH",
        "MOVE",
        "MOVEOUT",
        "NAME",
        "NATIONAL",
        "NATIVE",
        "NATURAL",
        "NCHAR",
        "NETWORK",
        "NEW",
        "NEXT",
        "NO",
        "NOCREATEDB",
        "NOCREATEUSER",
        "NODE",
        "NODES",
        "NONE",
        "NOT",
        "NOTHING",
        "NOTIFIER",
        "NOTIFY",
        "NOTNULL",
        "NOWAIT",
        "NULL",
        "NULLAWARE",
        "NULLCOLS",
        "NULLS",
        "NULLSEQUAL",
        "NUMBER",
        "NUMERIC",
        "OBJECT",
        "OCTETS",
        "OF",
        "OFF",
        "OFFSET",
        "OIDS",
        "OLD",
        "ON",
        "ONLY",
        "OPERATOR",
        "OPT",
        "OPTIMIZER",
        "OPTION",
        "OPTVER",
        "OR",
        "ORC",
        "ORDER",
        "OTHERS",
        "OUT",
        "OUTER",
        "OVER",
        "OVERLAPS",
        "OVERLAY",
        "OWNER",
        "PARAMETER",
        "PARAMETERS",
        "PARQUET",
        "PARSER",
        "PARTIAL",
        "PARTITION",
        "PARTITIONING",
        "PASSWORD",
        "PASSWORD_GRACE_TIME",
        "PASSWORD_LIFE_TIME",
        "PASSWORD_LOCK_TIME",
        "PASSWORD_MAX_LENGTH",
        "PASSWORD_MIN_DIGITS",
        "PASSWORD_MIN_LENGTH",
        "PASSWORD_MIN_LETTERS",
        "PASSWORD_MIN_LOWERCASE_LETTERS",
        "PASSWORD_MIN_SYMBOLS",
        "PASSWORD_MIN_UPPERCASE_LETTERS",
        "PASSWORD_REUSE_MAX",
        "PASSWORD_REUSE_TIME",
        "PATTERN",
        "PERCENT",
        "PERMANENT",
        "PINNED",
        "PLACING",
        "PLANNEDCONCURRENCY",
        "POLICY",
        "POOL",
        "PORT",
        "POSITION",
        "PRECEDING",
        "PRECISION",
        "PREPARE",
        "PREPASS",
        "PRESERVE",
        "PREVIOUS",
        "PRIMARY",
        "PRIOR",
        "PRIORITY",
        "PRIVILEGES",
        "PROCEDURAL",
        "PROCEDURE",
        "PROFILE",
        "PROJECTION",
        "PROJECTIONS",
        "PSDATE",
        "QUERY",
        "QUEUETIMEOUT",
        "QUOTE",
        "RANGE",
        "RAW",
        "READ",
        "REAL",
        "RECHECK",
        "RECORD",
        "RECOVER",
        "RECURSIVE",
        "REFERENCES",
        "REFRESH",
        "REINDEX",
        "REJECTED",
        "REJECTMAX",
        "RELATIVE",
        "RELEASE",
        "REMOVE",
        "RENAME",
        "REORGANIZE",
        "REPEATABLE",
        "REPLACE",
        "RESET",
        "RESOURCE",
        "RESTART",
        "RESTRICT",
        "RESULTS",
        "RETURN",
        "RETURNREJECTED",
        "REVOKE",
        "RIGHT",
        "RLE",
        "ROLE",
        "ROLES",
        "ROLLBACK",
        "ROLLUP",
        "ROW",
        "ROWS",
        "RULE",
        "RUNTIMECAP",
        "RUNTIMEPRIORITY",
        "RUNTIMEPRIORITYTHRESHOLD",
        "SAVE",
        "SAVEPOINT",
        "SCHEMA",
        "SCROLL",
        "SEARCH_PATH",
        "SECOND",
        "SECONDS",
        "SECURITY",
        "SECURITY_ALGORITHM",
        "SEGMENTED",
        "SELECT",
        "SEMI",
        "SEMIALL",
        "SEQUENCE",
        "SEQUENCES",
        "SERIALIZABLE",
        "SESSION",
        "SESSION_USER",
        "SET",
        "SETOF",
        "SETS",
        "SHARE",
        "SHARED",
        "SHOW",
        "SIMILAR",
        "SIMPLE",
        "SINGLEINITIATOR",
        "SITE",
        "SITES",
        "SKIP",
        "SMALLDATETIME",
        "SMALLINT",
        "SOME",
        "SOURCE",
        "SPLIT",
        "STABLE",
        "STANDBY",
        "START",
        "STATEMENT",
        "STATISTICS",
        "STDIN",
        "STDOUT",
        "STEMMER",
        "STORAGE",
        "STREAM",
        "STRENGTH",
        "STRICT",
        "SUBNET",
        "SUBSTRING",
        "SYSDATE",
        "SYSID",
        "SYSTEM",
        "TABLE",
        "TABLES",
        "TABLESAMPLE",
        "TABLESPACE",
        "TEMP",
        "TEMPLATE",
        "TEMPORARY",
        "TEMPSPACECAP",
        "TERMINATOR",
        "TEXT",
        "THAN",
        "THEN",
        "TIES",
        "TIME",
        "TIMESERIES",
        "TIMESTAMP",
        "TIMESTAMPADD",
        "TIMESTAMPDIFF",
        "TIMESTAMPTZ",
        "TIMETZ",
        "TIMEZONE",
        "TINYINT",
        "TLS",
        "TO",
        "TOAST",
        "TOKENIZER",
        "TOLERANCE",
        "TRAILING",
        "TRANSACTION",
        "TRANSFORM",
        "TREAT",
        "TRICKLE",
        "TRIGGER",
        "TRIM",
        "TRUE",
        "TRUNCATE",
        "TRUSTED",
        "TUNING",
        "TYPE",
        "UDPARAMETER",
        "UNBOUNDED",
        "UNCOMMITTED",
        "UNCOMPRESSED",
        "UNI",
        "UNINDEXED",
        "UNION",
        "UNIQUE",
        "UNKNOWN",
        "UNLIMITED",
        "UNLISTEN",
        "UNLOCK",
        "UNPACKER",
        "UNSEGMENTED",
        "UPDATE",
        "USAGE",
        "USER",
        "USING",
        "UUID",
        "VACUUM",
        "VALIDATE",
        "VALIDATOR",
        "VALINDEX",
        "VALUE",
        "VALUES",
        "VARBINARY",
        "VARCHAR",
        "VARCHAR2",
        "VARYING",
        "VERBOSE",
        "VERTICA",
        "VIEW",
        "VOLATILE",
        "WAIT",
        "WEBHDFS_ADDRESS",
        "WEBSERVICE_HOSTNAME",
        "WEBSERVICE_PORT",
        "WHEN",
        "WHERE",
        "WINDOW",
        "WITH",
        "WITHIN",
        "WITHOUT",
        "WORK",
        "WRITE",
        "YEAR",
        "ZONE"};
  }

  @Override
  public String getSQLColumnExists(String columnname, String tablename) {
    return super.getSQLColumnExists(columnname, tablename) + getLimitClause(1);
  }

  @Override
  public String getSQLQueryFields(String tableName) {
    return super.getSQLQueryFields(tableName) + getLimitClause(1);
  }

  @Override
  public String getSQLTableExists(String tablename) {
    return super.getSQLTableExists(tablename) + getLimitClause(1);
  }

  @Override
  public String[] getViewTypes() {
    return new String[]{};
  }

  @Override
  public boolean supportsAutoInc() {
    return false;
  }

  @Override
  public boolean checkIndexExists(Database database, String schemaName, String tableName, String[] idx_fields ) throws KettleDatabaseException {
    // no explicit index handling, indexes are not exposed. Assume all indexes are there!
    return true;
  }

  @Override
  public boolean supportsBooleanDataType() {
    return true;
  }

  @Override
  public boolean requiresCastToVariousForIsNull() {
    return true;
  }

  @Override
  public String getExtraOptionIndicator() {
    return "?";
  }

  @Override
  public String getExtraOptionSeparator() {
    return "&";
  }

  @Override
  public boolean supportsSequences() {
    return true;
  }

  @Override
  public String getSQLSequenceExists(String sequenceName) {
    return "SELECT sequence_name FROM sequences WHERE sequence_name = '" + sequenceName + "'";
  }

  @Override
  public String getSQLListOfSequences() {
    return "SELECT sequence_name FROM sequences";
  }

  @Override
  public String getSQLCurrentSequenceValue(String sequenceName) {
    return "SELECT currval('" + sequenceName + "')";
  }

  @Override
  public String getSQLNextSequenceValue(String sequenceName) {
    return "SELECT nextval('" + sequenceName + "')";
  }

  @Override
  public boolean supportsTimeStampToDateConversion() {
    return true;
  }

  @Override
  public boolean supportsGetBlob() {
    return false;
  }

  @Override
  public boolean isDisplaySizeTwiceThePrecision() {
    return true;
  }

  @Override
  public Object getValueFromResultSet(ResultSet rs, ValueMetaInterface val, int index) throws KettleDatabaseException {
    Object data;

    try {
      switch (val.getType()) {
        case ValueMetaInterface.TYPE_TIMESTAMP:
        case ValueMetaInterface.TYPE_DATE:
          if (val.getOriginalColumnType() == java.sql.Types.TIMESTAMP) {
            data = rs.getTimestamp(index + 1);
            break;
          } else if (val.getOriginalColumnType() == java.sql.Types.TIME) {
            data = rs.getTime(index + 1);
            break;
          } else {
            data = rs.getDate(index + 1);
            break;
          }
        default:
          return super.getValueFromResultSet(rs, val, index);
      }
      if (rs.wasNull()) {
        data = null;
      }
    } catch (SQLException e) {
      throw new KettleDatabaseException("Unable to get value '"
          + val.toStringMeta() + "' from database resultset, index " + index, e);
    }

    return data;
  }
}
