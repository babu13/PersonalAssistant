package combo.parvez.kamal.sakib.personalassistant;

/**
 * Created by sakib on 11/6/17.
 */

public class Blocklist {
    public long id;
    public String phoneNumber;

    // Default constructor
    public Blocklist() {

    }

    // To easily create Blacklist object, an alternative constructor
    public Blocklist(final String phoneMumber) {
        this.phoneNumber = phoneMumber;
    }

    // Overriding the default method to compare between the two objects bu phone number
    @Override
    public boolean equals(final Object obj) {

        // If passed object is an instance of Blacklist, then compare the phone numbers, else return false as they are not equal
        if(obj.getClass().isInstance(new Blocklist()))
        {
            // Cast the object to Blacklist
            final Blocklist bl = (Blocklist) obj;

            // Compare whether the phone numbers are same, if yes, it defines the objects are equal
            if(bl.phoneNumber.equalsIgnoreCase(this.phoneNumber))
                return true;
        }
        return false;
    }
}

