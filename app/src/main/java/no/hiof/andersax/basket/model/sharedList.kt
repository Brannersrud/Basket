package no.hiof.andersax.basket.model

class sharedList ( val members : ArrayList<String>,
                  listname: String,
                  description: String,
                  Owner: String, items : List<ListItem>
) : ListCollection( listname, description, Owner, items) {


    fun addMembersToList(email : String){
        members.add(email)
    }
    fun removeMember(email : String){
        members.remove(email)
    }





}