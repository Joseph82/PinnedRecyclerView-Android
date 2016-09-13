# PinnedList Android library
This library allows you to create a list of items that are pinned by a floating label (text or image) on the left of the list .

## PinnedList in action

![Text Pinned Demo](https://www.dropbox.com/s/yu2558bbmv3twc5/text_demo1.gif?raw=true)

![Image Pinned Demo](https://www.dropbox.com/s/t7m7yd8vh6w4jib/image_demo1.gif?raw=true)

## Usage

### Add library

#### Gradle

```groovy 
compile 'com.gvillani:pinnedlist:0.9.2'
```
#### Maven

```xml 
<dependency>
  <groupId>com.gvillani</groupId>
  <artifactId>pinnedlist</artifactId>
  <version>0.9.2</version>
  <type>pom</type>
</dependency>
```

### Pinned Text List

Add the `PinnedListLayout` in your Layout.

```xml
<com.gvillani.pinnedlist.PinnedListLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/pinned_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:pin_text_color="@color/colorAccent"
    app:type="text"
    app:fade_effect="true"
    app:pin_vertical_position="top"
    app:pin_text_size="30sp">
```

Get a reference to the PinnedLayout and get the RecyclerView that it contains.

```Java
mListLayout = (PinnedListLayout)findViewById(R.id.pinned_layout);
mRecyclerView = mListLayout.getRecyclerView();
```

Create a `GroupListWrapper` with the static method `createAlphabeticList()`. You can also create it manually managing each group, but if you want a simple alphabetically ordered  list using create `createAlphabeticList()` is the fastest way.

```Java
GroupListWrapper listGroup = GroupListWrapper.createAlphabeticList(contacts, GroupListWrapper.ASCENDING);
```

And finally instantiate your custom Adapter (it must extend `PinnedAdapter`) and set it to the RecyclerView

```Java
mListAdapter = new ContactAdapter(this, listGroup, mListLayout);
mRecyclerView.setAdapter(mListAdapter);
```

Moreover, if you want create a GroupListWrapper using the `createAlphabeticList` you need to tell the system which label will be applied for your object and the related order. For example if you have a Contact object that contains name and surname you can do something like this:

```Java
public class Contact implements GroupListWrapper.Selector{
    private String name;
    private String surname;

    @Override
    public String select() {
        return name+surname;
    }
}
```

In this way you are telling the system that you want that your pin label will have the first letter of the name and they should be ordered using the name and the surname (if the name is the same).

### Pinned Image List

In order to use a pinned list with image you need to set in your layout:

`app:type="image"`

You create your `GroupListWrapper`

```Java
GroupListWrapper listGroup = new GroupListWrapper();
```

Then you need to create a list of wrapping object (ItemPinned) that contain your costom object (for example a Contact) and then you add

```Java
ItemPinned itemD1 = new ImageItemPinned(new Contact("Ben","Weber", R.drawable.contact1)); 
ItemPinned itemD2 = new ImageItemPinned(new Contact("Emma","Hartmann", R.drawable.contact9)); 

List<ItemPinned> listD = new ArrayList<>();
listD.add(itemD1);
listD.add(itemD2);

Group groupD = new Group(listD, R.drawable.germany);
```

And eventually you add one or more `Group` object to the `GroupListWrapper`

```Java
listGroup.addGroup(groupD);
```

Finally instantiate your custom Adapter (it must extend `PinnedAdapter`) and set it to the RecyclerView

```Java
mListAdapter = new ContactAdapter(this, listGroup, mListLayout);
mRecyclerView.setAdapter(mListAdapter);
```

### The Adapter

As before mentioned, you need to create your custom Adapter extending `PinnedAdapter` class. 

When you override the method `onCreateViewHolder` you need to create your custom row layout (for example using inflater) and then you need to allow the system to wrap that layout inside a layout that contains the pin. So, you simply need to obtain a wrapped version of your layout passing it to `getRowLayout()` as shown in the example below:

```Java
@Override
public PinnedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mLayoutInflater.inflate(R.layout.item_contact, parent, false);
    ViewHolderContact viewHolderContact = new ViewHolderContact(getRowLayout(view));
    return viewHolderContact;
}
```

## Note

Currently the Library is created as a compound View wrapped inside a CoordinatorLayout. So, if you need to use a CoordinatorLayout you can use it: for example you can add a Toolbar inside it, or a FAB that will be automatically coordinated with the Snackbar.

The library support only the LinearLayoutManager, so if you try to set a different layout it will be ignored.

## TODO

* Allow the user to manipulate the content of the adapter without replacing the entire dataset.
* Support for `GridLayoutManager`
* Find different strategy that make the wrapping of the custom layout inside the pinner layout more transparent (user should not call `getRowLayout()` from his adapter)
* Allow groups manipulation 
