package com.graphql.tutorial.salesservice.utils;

public class Const {

    private Const(){}

    public static final String QUERY_SIMPLE_MODELS =
            """
           query simpleModels($modelIds: [Long!]!) {
             simpleModels(modelIds: $modelIds) {
               id
               name
               onTheRoadPrice
               exteriorColor
               interiorColor
               releaseYear
               transmission
               fuel
               bodyType
             }
           }            
            """;

    public static final String OPERATION_NAME_SIMPLE_MODELS = "simpleModels";
    public static final String VARIABLE_NAME_MODEL_UUIDS = "modelIds";
    public static final String PRODUCT_THREAD_POOL_EXECUTOR_NAME = "productThreadPoolExecutor";
}
