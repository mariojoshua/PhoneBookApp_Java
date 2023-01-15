```mermaid
classDiagram
    PhoneBookApp 
    PhoneBookApp *-- PhoneBookManager
    PhoneBookManager *-- ContactBean
    PhoneBookManager *-- PhoneBook
    PhoneBookManager *-- PhoneBookService
    class PhoneBookApp {
        +main()
    }
    class PhoneBookManager {
        +run()
        +mainMenu()
        +createContactsBookMenu()
        +listContactsMenu()
    }
    class PhoneBookService {
        +searchContacts()
        +createContactsBook()
        +addContact()
        +editContact()
        +removeContact() 
    }
    PhoneBookService *-- IOService
    class IOService {
        <<Inteface>>
        +readContact()
        +writeContact()
        +updateContact()
        +createContactBook
        +deleteContact()
        +deleteContactBook()
    }

    IOService *-- PlainTextIOService : implements
    IOService *-- SerializedTextIOService : implements
    IOService <|-- MySQLService : implements

    class PlainTextIOService {

    }
    class SerializedTextIOService {

    }
    class MySQLService {

    }

    class ContactBean {
        <<Record>>
        +Name name
        +List~PhoneNumber~ phoneNumbers
        +List~Address~ address 
        +List~String~ tags 
        +List~Email~ email 
        +Date dateOfBirth
    }
    Serializable <|-- ContactBean: Implements
    PhoneBook <|-- Contact
    PhoneBook : +int count
    PhoneBook : +String Category
    PhoneBook: +equals()
    PhoneBook: +hashcode()
    PhoneBook: +toString()
 
    Contact <-- Name
    class Contact{
        +Name name
        +List~String~ phoneNumbers
        +List~String~ address 
        +List~String~ tags 
        +List~String~ email 
        +Date dateOfBirth
        +equals()
        +hashcode()
        +toString()
    }
    class Name {
        Pronoun type
        String fullName
        String petName
        +equals()
        +hashcode()
        +toString()
    }
    Name <-- Pronoun
    class Pronoun {
        <<Enumeration>>
        he/him 
        she/her
        they/them
    }

```    