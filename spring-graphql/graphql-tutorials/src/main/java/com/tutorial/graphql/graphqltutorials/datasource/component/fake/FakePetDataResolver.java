package com.tutorial.graphql.graphqltutorials.datasource.component.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.tutorial.graphql.graphqltutorials.generated.DgsConstants;
import com.tutorial.graphql.graphqltutorials.generated.types.Cat;
import com.tutorial.graphql.graphqltutorials.generated.types.Dog;
import com.tutorial.graphql.graphqltutorials.generated.types.Pet;
import com.tutorial.graphql.graphqltutorials.generated.types.PetFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tutorial.graphql.graphqltutorials.fake.FakePet.PETS_LIST;

@DgsComponent
public class FakePetDataResolver {

    @DgsQuery(field = DgsConstants.QUERY.Pets)
    public List<Pet> pets(@InputArgument(name = "petFilter")Optional<PetFilter> filter) {


        if(filter.isEmpty()){
            return PETS_LIST;
        }

        return PETS_LIST.stream().filter(p -> this.matchFilter(p, filter.get())).collect(Collectors.toList());
    }

    private boolean matchFilter(Pet p, PetFilter filter) {
        if (StringUtils.isBlank(filter.getPetType())){
            return true;
        }

        if(filter.getPetType().equalsIgnoreCase(Dog.class.getSimpleName())){
            return p instanceof Dog;
        }else if(filter.getPetType().equalsIgnoreCase(Cat.class.getSimpleName())){
            return p instanceof Cat;
        }else {
            return false;
        }
    }

}
