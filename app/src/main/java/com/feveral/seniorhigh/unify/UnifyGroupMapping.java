package com.feveral.seniorhigh.unify;

import com.feveral.seniorhigh.R;

/**
 * Created by feveral on 2017/8/17.
 */

public class UnifyGroupMapping {

    public static String positionToGroup(int position){
        if(position==1)
            return "機械";
        else if(position==2)
            return "動機";
        else if(position==3)
            return "電機";
        else if(position==4)
            return "資電";
        else if(position==5)
            return "化工";
        else if(position==6)
            return "土木";
        else if(position==7)
            return "設計";
        else if(position==8)
            return "工管";
        else if(position==9)
            return "商管";
        else if(position==10)
            return "衛護";
        else if(position==11)
            return "食品";
        else if(position==12)
            return "幼保";
        else if(position==13)
            return "生活";
        else if(position==14)
            return "農業";
        else if(position==15)
            return "英語";
        else if(position==16)
            return "日語";
        else if(position==17)
            return "餐旅";
        else if(position==18)
            return "海事";
        else if(position==19)
            return "水產";
        else if(position==20)
            return "藝影";
        return "";
    }

    public static int positionToRecyclerId(int position){
        if(position==1)
            return R.id.recycler_unify_group_1;
        else if(position==2)
            return R.id.recycler_unify_group_2;
        else if(position==3)
            return R.id.recycler_unify_group_3;
        else if(position==4)
            return R.id.recycler_unify_group_4;
        else if(position==5)
            return R.id.recycler_unify_group_5;
        else if(position==6)
            return R.id.recycler_unify_group_6;
        else if(position==7)
            return R.id.recycler_unify_group_7;
        else if(position==8)
            return R.id.recycler_unify_group_8;
        else if(position==9)
            return R.id.recycler_unify_group_9;
        else if(position==10)
            return R.id.recycler_unify_group_10;
        else if(position==11)
            return R.id.recycler_unify_group_11;
        else if(position==12)
            return R.id.recycler_unify_group_12;
        else if(position==13)
            return R.id.recycler_unify_group_13;
        else if(position==14)
            return R.id.recycler_unify_group_14;
        else if(position==15)
            return R.id.recycler_unify_group_15;
        else if(position==16)
            return R.id.recycler_unify_group_16;
        else if(position==17)
            return R.id.recycler_unify_group_17;
        else if(position==18)
            return R.id.recycler_unify_group_18;
        else if(position==19)
            return R.id.recycler_unify_group_19;
        else if(position==20)
            return R.id.recycler_unify_group_20;
        return 0;
    }

    public static int positionToLayoutId(int position){
        if(position==1)
            return R.layout.unify_group_1;
        else if(position==2)
            return R.layout.unify_group_2;
        else if(position==3)
            return R.layout.unify_group_3;
        else if(position==4)
            return R.layout.unify_group_4;
        else if(position==5)
            return R.layout.unify_group_5;
        else if(position==6)
            return R.layout.unify_group_6;
        else if(position==7)
            return R.layout.unify_group_7;
        else if(position==8)
            return R.layout.unify_group_8;
        else if(position==9)
            return R.layout.unify_group_9;
        else if(position==10)
            return R.layout.unify_group_10;
        else if(position==11)
            return R.layout.unify_group_11;
        else if(position==12)
            return R.layout.unify_group_12;
        else if(position==13)
            return R.layout.unify_group_13;
        else if(position==14)
            return R.layout.unify_group_14;
        else if(position==15)
            return R.layout.unify_group_15;
        else if(position==16)
            return R.layout.unify_group_16;
        else if(position==17)
            return R.layout.unify_group_17;
        else if(position==18)
            return R.layout.unify_group_18;
        else if(position==19)
            return R.layout.unify_group_19;
        else if(position==20)
            return R.layout.unify_group_20;
        else
            return 0;
    }
}
