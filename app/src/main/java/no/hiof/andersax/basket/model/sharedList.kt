package no.hiof.andersax.basket.model

class sharedList (val members : MutableList<ListMembers>,
                  var usernames : MutableList<String>,
                  listname: String ,
                  description: String,
                  Owner: String , items : MutableList<ListItem>, totalPrice : Long
) : ListCollection( listname , description , Owner , items, totalPrice)  {




constructor() : this(members=ArrayList<ListMembers>(),usernames = ArrayList<String>(), listname = "", description = "",
    Owner = "", items = ArrayList<ListItem>(), totalPrice = 0)

}