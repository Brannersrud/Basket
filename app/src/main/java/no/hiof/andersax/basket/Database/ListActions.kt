package no.hiof.andersax.basket.Database

import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.andersax.basket.model.ListCollection
import no.hiof.andersax.basket.model.sharedList
import no.hiof.andersax.basket.view.createListFragment
import no.hiof.andersax.basket.view.privateListFragment

class ListActions {
    private var Auth: AuthActions = AuthActions()


    fun addPrivateList(list: ListCollection, uid: String, fragment: privateListFragment) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList").document(uid)
        ref.get()
            .addOnCompleteListener { Task ->
                if(Task.isSuccessful){
                    overWritePrivateList(uid, list,fragment)
                }else{
                    addNewPrivateList(list, fragment)
                }
            }
    }

    private fun addNewPrivateList(list: ListCollection, fragment: privateListFragment) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList")

        ref.add(list)
            .addOnCompleteListener { Task ->
                if(Task.isSuccessful){
                    fragment.showToastToUser("List added beautifully")
                }else{
                    fragment.showToastToUser("List rejected violently")
                }
            }

    }
    private fun overWritePrivateList(uid: String, list: ListCollection, fragment: privateListFragment) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList").document(uid)
        ref.update("items", list.getListItems())
            .addOnCompleteListener {Task ->
                if(Task.isSuccessful){
                    fragment.showToastToUser("List updated gracefully")
                }else{
                    fragment.showToastToUser("Unable to update list, obnoxiously")
                }

            }
    }

     fun addNewSharedList(list:sharedList, fragment : createListFragment){
         val db = FirebaseFirestore.getInstance()
         val ref = db.collection("sharedList")

         ref.add(list)
             .addOnCompleteListener { Task ->
                 if(Task.isSuccessful){
                     //do some fancy stuff, toast for user
                     fragment.showToastToUser("List created carefully")
                 }else{
                     fragment.showToastToUser("List denied awfully")
                 }
             }
     }

/*
    fun getPrivateLists(presenter: ListPresenter) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList")
        var lists: ArrayList<ListCollection> = ArrayList()
        ref.whereEqualTo("owner", Auth.getCurrentUser().email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var l: List<ListItem> = document.get("items")!! as List<ListItem>
                    var owner = document.get("owner").toString()
                    var listname = document.get("listname").toString()
                    var description = document.get("description").toString()
                    var listcollection = ListCollection(listname, description, owner, l)
                    listcollection.setUid(document.id)

                    lists.add(listcollection)

                }
                presenter.setPrivateLists(lists)

            }
    }
    fun getListItems(uid : String, presenter: ListPresenter) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("privateList").document(uid)
        var lists: List<ListItem> = ArrayList()
        ref.get()
            .addOnSuccessListener { it ->
                    var l: List<ListItem> = it.data!!.get("items") as List<ListItem>



                lists = l;


                presenter.setListItems(lists)
            }

    }

 */
}







/*
    fun setPrivateLists(lists : ArrayList<List>){
        presenter.setPrivateLists(lists)
    }

*/

