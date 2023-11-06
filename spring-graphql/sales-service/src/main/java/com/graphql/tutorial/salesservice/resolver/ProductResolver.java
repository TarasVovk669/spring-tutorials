package com.graphql.tutorial.salesservice.resolver;

import com.graphql.tutorial.salesservice.dataloader.ProductDataLoader;
import com.graphql.tutorial.salesservice.generated.DgsConstants;
import com.graphql.tutorial.salesservice.generated.types.SalesOrderItem;
import com.graphql.tutorial.salesservice.generated.types.SimpleModel;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import org.dataloader.DataLoader;

import java.util.concurrent.CompletableFuture;

@DgsComponent
public class ProductResolver {

    @DgsData(parentType = DgsConstants.SALESORDERITEM.TYPE_NAME,
            field = DgsConstants.SALESORDERITEM.ModelDetail)
    public CompletableFuture<SimpleModel> loadSimpleData(DgsDataFetchingEnvironment env) {
        DataLoader<Long,SimpleModel> dataLoader = env.getDataLoader(ProductDataLoader.class);  // data loader graphQL
        //DataLoader<Long, SimpleModel> dataLoader = env.getDataLoader(ProductDataLoaderRest.class); //data loader rest
        SalesOrderItem salesOrderItem = env.getSource();

        return dataLoader.load(salesOrderItem.getModelId());

    }
}
