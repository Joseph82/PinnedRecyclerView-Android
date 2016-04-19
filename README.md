# PinnedList Android library
This library allows you to create a list of items that are pinned by a floating label (text or image).

## PinnedList in action

![Text Pinned Demo](https://www.dropbox.com/s/pbj3ze5qw694941/text_demo.gif?raw=true)

![Image Pinned Demo](https://www.dropbox.com/s/tcll7mhqh6tc4l5/image_demo.gif?raw=true)

## Usage

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

Create a `GroupListWrapper` with the static method `createAlphabeticList()`

```Java
GroupListWrapper listGroup = GroupListWrapper.createAlphabeticList(contacts, GroupListWrapper.ASCENDING);
```

And finally instantiate your custom Adapter (it must extend `PinnedAdapter`) and set it to the RecyclerView

```Java
mListAdapter = new ContactAdapter(this, listGroup, mListLayout);
mRecyclerView.setAdapter(mListAdapter);
```

### Pinned Image List

In order to use a pinned list with image you need to set in your layout:

`app:type="text"`

You create your `GroupListWrapper`

```Java
GroupListWrapper listGroup = new GroupListWrapper();
```

Then you need to create a list of wrapping object (ItemPinned) that contain your costom object (for example a Contact) and then you add

```Java
ItemPinned<Contact> itemD1 = new ImageItemPinned<>(new Contact("Ben","Weber", R.drawable.contact1)); 
ItemPinned<Contact> itemD2 = new ImageItemPinned<>(new Contact("Emma","Hartmann", R.drawable.contact9)); 

List<ItemPinned<Contact>> listD = new ArrayList<>();
listD.add(itemD1);
listD.add(itemD2);

Group<Contact> groupD = new Group<>(listD, R.drawable.germany);
```

And eventually you add one or more `Group` object to the `GroupListWrapper`

```Java
listGroup.addGroup(groupD);
```

And finally instantiate your custom Adapter (it must extend `PinnedAdapter`) and set it to the RecyclerView

```Java
mListAdapter = new ContactAdapter(this, listGroup, mListLayout);
mRecyclerView.setAdapter(mListAdapter);
```

## Note

Currently the Library is created as a compound View wrapped inside a CoordinatorLayout. So, if you need to use a CoordinatorLayout you can use it: for example you can add a Toolbar inside it, or a FAB that will be automatically coordinated with the Snackbar.

The library support only the LinearLayoutManager, so if you try to set a different layout it will be ignored.

## TODO

* Allow the user to manipulate the content of the adapter without replacing the entire dataset.
