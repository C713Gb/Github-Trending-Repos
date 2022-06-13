package com.banerjeec713.githubassignment;

import com.banerjeec713.githubassignment.data.models.TrendingItemModel;

import java.util.Collections;
import java.util.List;

public class Utils {

    public static List<TrendingItemModel> generateTestOneItemModel(){
        return Collections.singletonList(new TrendingItemModel("GBEE", "http://www.test.com"
                , "http://www.test.com"
                , "http://www.test.com"
                , "Testing"
                ,"Java"
                ,"#3572A5"
                , 6490
                , 451
                , 586));
    }


}
