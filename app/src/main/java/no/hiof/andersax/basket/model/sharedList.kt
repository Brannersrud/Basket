package no.hiof.andersax.basket.model

class sharedList (val members : ArrayList<User>,
                  listname: String,
                  description: String,
                  Owner: String, items: ArrayList<ListItem>
) : List(listname, description, Owner, items) {



    fun addMembersToList(user : User){
        members.add(user)
    }
    fun removeMember(user: User){
        members.remove(user)
    }





}