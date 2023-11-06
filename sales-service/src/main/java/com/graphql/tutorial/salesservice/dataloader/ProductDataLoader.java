package com.graphql.tutorial.salesservice.dataloader;

import com.graphql.tutorial.salesservice.generated.types.SimpleModel;
import com.graphql.tutorial.salesservice.service.ProductQueryService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static com.graphql.tutorial.salesservice.utils.Const.PRODUCT_THREAD_POOL_EXECUTOR_NAME;

@DgsDataLoader(name = "productDataLoader")
public class ProductDataLoader implements MappedBatchLoader<Long, SimpleModel> {

    private final ProductQueryService productQueryService;
    private final Executor dataLoaderExecutor;

    public ProductDataLoader(ProductQueryService productQueryService,
                             @Qualifier(PRODUCT_THREAD_POOL_EXECUTOR_NAME) Executor dataLoaderExecutor) {
        this.productQueryService = productQueryService;
        this.dataLoaderExecutor = dataLoaderExecutor;
    }

    @Override
    public CompletionStage<Map<Long, SimpleModel>> load(Set<Long> keys) {
        return CompletableFuture.supplyAsync(() -> productQueryService.loadSimpleModel(keys), dataLoaderExecutor);
    }
}
