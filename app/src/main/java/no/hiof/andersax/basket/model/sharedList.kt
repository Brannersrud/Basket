package no.hiof.andersax.basket.model

class sharedList ( val members : ArrayList<String>,
                  listname: String,
                  description: String,
                   phone : String,
                  Owner: String, items : MutableList<ListItem>, totalPrice : Long
) : ListCollection( listname, description, Owner, items, totalPrice)  {


    fun addMembersToList(email : String){
        members.add(email)
    }
    fun removeMember(email : String){
        members.remove(email)
    }








}