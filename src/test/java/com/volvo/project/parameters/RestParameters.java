package com.volvo.project.parameters;

public class RestParameters {

  // common strings
  public static final String NULL_STRING = "null";

  //status codes
  public static final int OK_STATUS_CODE = 200;
  public static final int NO_CONTENT_CODE = 204;
  public static final int CREATED_STATUS_CODE = 201;

  //rest url
  public static final String PRODUCT_COST_CALCULATION_FRAMEWORK_RESOURCE_URL = "v1/frameworks";
  public static final String FUNCTION_GROUP_RESOURCE_URL = "v1/function-groups";
  public static final String PRODUCT_COST_CALCULATION_FRAMEWORK_SEARCH_RESOURCE_URL = "v1/frameworks/search";
  public static final String AVAILABLE_PROJECT_RESOURCE_URL = "v1/available-projects";
  public static final String CONTAINER_RESOURCE_URL = "v1/ve-template-containers";
  public static final String APP_USER_RESOURCE_URL = "v1/app-users/ldap";
  //keys
  public static final String NAME_KEY = "name";
  public static final String ID_KEY = "id";
  public static final String IDS_KEY = "ids";
  public static final String TOTAL_COUNT_KEY = "totalCount";

  //params
  public static final String SEARCH_PARAM = "search";
  public static final String SEARCH_TERM_PARAM = "searchTerm";
  public static final String PAGE_SIZE_PARAM = "page-size";
  public static final String TERM_PARAM = "term";
  public static final String TYPE_PARAM = "type";
  public static final Integer MAX_RESULTS = 100000;

  //json path queries
  public static final String FIND_ALL_BY_ID = "findAll { it.id ==";
  public static final String FIND_BY_ID = "find {it.id ==";
  public static final String FIRST_ARRAY_ELEMENT = "[0]";
  public static final String FIRST_ITEMS_ELEMENT = "items" + FIRST_ARRAY_ELEMENT + ".";
  public static final String FIND_IN_ITEMS_BY_ID = "items.find { it.id ==";

  //defaults
  public static final String ARV_2018_CD_PPNLT_BOM_NAME = "ARV-2018-CD-PPNLT";
}
