package com.example.faiz.carwash.Activities.Activities.UI;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faiz.carwash.Activities.Activities.Activities.ProfileActivity;
import com.example.faiz.carwash.Activities.Activities.Model.Apartment;
import com.example.faiz.carwash.Activities.Activities.Model.Owner_Services;
import com.example.faiz.carwash.Activities.Activities.Model.Supervisor;
import com.example.faiz.carwash.Activities.Activities.Model.UserModel;
import com.example.faiz.carwash.Activities.Activities.Model.Vehicle_TypeObject;
import com.example.faiz.carwash.Activities.Activities.Utils.AppController;
import com.example.faiz.carwash.Activities.Activities.Utils.AppLogs;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AST on 9/15/2017.
 */

public class Owner_Profile_Edit extends android.support.v4.app.Fragment {
    public static TextView ActionBartitle;
    public Button update_payment;
    public ImageView back_arrow, add_service, add_vehicle;
    public TextView user_name, user_phone, user_address;
    private FirebaseAuth mAuth;
    private FirebaseUser firebase_user;
    private DatabaseReference firebase;
    public TextView add_appartment;
    public TextView add_supervisor;
    public CircleImageView profile_pic_owner;
    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_READ_CONTACTS = 2;
    private String downloadURL = "";
    public Uri mCapturedImageURI;
    public Intent intent;
    private String imgPath;
    private ProgressDialog mProgressDialog;
    private StorageReference rootStorageRef, imageRef, folderRef, fileStorageRef;
    private static final int SELECTED_PICTURE = 9162;
    public Button save_changes_profile;
    public LinearLayout info_box;
    public FlexboxLayout flexBoxLayout_appartment, flexBoxLayout_supervisor, flexBoxLayout_vehicle_type, flexBoxLayout_service;
    private ArrayList<TextView> TextArrayList;
    public String vehicle_type[] = {"Small Car", "Luxury Car", "Bike"};
    public String servie_flex[] = {"Interior Detailing", "Exterior Detailing", "Washing", "Mopping"};
    ProfileActivity parent;
    ProgressDialog progressDialog;
    int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.profile_edit_fragment, null);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        initializeView(view);


        FirebaseHandler.getInstance().getAdd_apartment().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flexBoxLayout_appartment.removeAllViews();
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        int i = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            AppLogs.d("TAG_SNAP", snapshot.getValue().toString());
                            Apartment apartment = snapshot.getValue(Apartment.class);
                            flexBoxLayout_appartment.addView(edittext(i, apartment, apartment.getApartmentName()));
                            i++;
                        }

                        flexBoxLayout_appartment.addView(Optionedittext(i, "", "ADD NEW APARTMENT"));

                    } else {
                        flexBoxLayout_appartment.addView(Optionedittext(1, "", "ADD NEW APARTMENT"));
                    }
                    progressDialog.hide();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseHandler.getInstance().getAdd_supervisor().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //   if (getActivity() != null) {
                flexBoxLayout_supervisor.removeAllViews();
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        int i = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            AppLogs.d("TAG_SNAP", snapshot.getValue().toString());
                            Supervisor apartment = snapshot.getValue(Supervisor.class);
                            flexBoxLayout_supervisor.addView(Supertext(i, apartment, apartment.getSuperVisor_name()));
                            i++;
                        }

                        flexBoxLayout_supervisor.addView(superVisorText(i, "", "ADD SUPERVISOR / BOY"));

                    } else {
                        flexBoxLayout_supervisor.addView(superVisorText(1, "", "ADD SUPERVISOR / BOY"));
                    }
                }
            }

            //    }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  ProfileActivity.getInstance().onBackPressed();
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                        //  .addToBackStack(null)
                        .replace(R.id.profile_container, new Owner_Profile_Details()).commit();
            }
        });

        add_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                final AlertDialog alertDialog1 = alertDialog.create();
                final View view1 = getActivity().getLayoutInflater().inflate(R.layout.add_services, null);
                Button add_dialog_ms = (Button) view1.findViewById(R.id.add_dialog_ms);
                final EditText apartment_cost = (EditText) view1.findViewById(R.id.apartment_cost);
                final EditText apartment_vehicle_type = (EditText) view1.findViewById(R.id.service_vehicle_type);
                final EditText apartment_service = (EditText) view1.findViewById(R.id.apartment_service);
                final Button cancle_dialog_ms = (Button) view1.findViewById(R.id.cancle_dialog_ms);
                //   final EditText editText = (EditText) view1.findViewById(R.id.block_name);
                //    StringBuilder str = new StringBuilder(apartment_cost.getText().toString());


                add_dialog_ms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (apartment_cost.getText().toString().length() ==0 || !apartment_cost.getText().toString().matches("^[0-9]+( [0-9]+)*$")) {
                            apartment_cost.setError("Enter Cost");
                        } else if (apartment_vehicle_type.getText().toString().length() ==0 || !apartment_vehicle_type.getText().toString().matches("^[a-zA-Z]+( [a-zA-z]+)*$")) {
                            apartment_vehicle_type.setError("Enter valid Vehicle type");
                        } else if (apartment_service.getText().toString().length() ==0 || !apartment_service.getText().toString().matches("^[a-zA-Z]+( [a-zA-z]+)*$")) {
                            apartment_service.setError("Enter valid Service");
                        } else {
                            final String tempCost = "₹" + apartment_cost.getText().toString();
                            DatabaseReference ref = FirebaseHandler.getInstance().getAdd_owner_serivces()
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push();
                            String tempKey = ref.getKey();
                            Owner_Services block = new Owner_Services(apartment_service.getText().toString()
                                    , tempCost,
                                    apartment_vehicle_type.getText().toString()
                                    , tempKey);

                            FirebaseHandler.getInstance().getAdd_owner_serivces()
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(tempKey)
                                    .setValue(block, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            alertDialog1.dismiss();
                                        }
                                    });
                        }
                    }
                });

                cancle_dialog_ms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                    }
                });


                alertDialog1.setView(view1);
                alertDialog1.show();

            }
        });

        add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                final AlertDialog alertDialog1 = alertDialog.create();
                final View view1 = getActivity().getLayoutInflater().inflate(R.layout.add_vehicle_type, null);
                Button add_dialog_ms = (Button) view1.findViewById(R.id.add_dialog_ms);
                final EditText apartment_cost = (EditText) view1.findViewById(R.id.vehicle_type);
                final Button cancle_dialog_ms = (Button) view1.findViewById(R.id.cancle_dialog_ms);
                //   final EditText editText = (EditText) view1.findViewById(R.id.block_name);
                //   StringBuilder str = new StringBuilder(apartment_cost.getText().toString());

                //   String tempCost  = str.insert(0,"₹").toString();
                add_dialog_ms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (apartment_cost.getText().toString().length() == 0 || !apartment_cost.getText().toString().matches("^[a-zA-Z]+( [a-zA-z]+)*$")) {
                            apartment_cost.setError("Enter valid Vehicle");


                        }else{
                            DatabaseReference ref = FirebaseHandler.getInstance().getAdd_vehicle_type()
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push();
                            String tempKey = ref.getKey();
                            Vehicle_TypeObject block = new Vehicle_TypeObject(apartment_cost.getText().toString(), tempKey);

                            FirebaseHandler.getInstance().getAdd_vehicle_type()
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(tempKey)
                                    .setValue(block, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            alertDialog1.dismiss();
                                        }
                                    });
                        }
                    }
                });

                cancle_dialog_ms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                    }
                });


                alertDialog1.setView(view1);
                alertDialog1.show();
            }
        });

        profile_pic_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Upload Image");
                alert.setMessage("Want to upload image..?");
                alert.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{
                                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    2);
                        } else {
                            //       if(Build.VERSION.SDK_INT >16) {
                            //          File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Images");
                            //         imagesFolder.mkdirs();
                            //         File image = new File(imagesFolder.getPath(), "MyImage_.jpg");
                            //        String fileName = "temp.jpg";
                            //           ContentValues values = new ContentValues();
                            //          values.put(MediaStore.Images.Media.TITLE, image.getAbsolutePath());
                            //           mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            //     }


                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            //     cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        }

                    }
                });

                alert.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  mayRequestContacts();
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{
                                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    2);
                        } else {

                            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, SELECTED_PICTURE);
                        }
                    }
                });


                alert.create().show();
            }
        });

        save_changes_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!downloadURL.equals("")) {
                    uploadImage(profile_pic_owner);
                }

                //   ProfileActivity.getInstance().onBackPressed();

                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.profile_container, new Owner_Profile_Details()).commit();
            }
        });

        FirebaseHandler.getInstance().getUsersRef().child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            if (dataSnapshot.getValue() != null) {
                                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                user_phone.setText(userModel.getEmail());
                                user_address.setText(userModel.getAddress());
                                user_name.setText(userModel.getFname() + "" + userModel.getLname());
                                if (userModel.getImageUrl().equals("")) {
                                    Glide.with(getActivity()).load(R.drawable.user).asBitmap().into(profile_pic_owner);

                                } else {
                                    Glide.with(getActivity()).load(userModel.getImageUrl()).asBitmap().placeholder(R.drawable.user).into(profile_pic_owner);

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        return view;
    }

    private void initializeView(View view) {
        profile_pic_owner = (CircleImageView) view.findViewById(R.id.profile_pic_owner);
        flexBoxLayout_appartment = (FlexboxLayout) view.findViewById(R.id.flexBoxLayout_appartment);
        flexBoxLayout_supervisor = (FlexboxLayout) view.findViewById(R.id.flexBoxLayout_supervisor);
        flexBoxLayout_vehicle_type = (FlexboxLayout) view.findViewById(R.id.flexBoxLayout_vehicle);
        flexBoxLayout_service = (FlexboxLayout) view.findViewById(R.id.flexBoxLayout_service);
        user_name = (TextView) view.findViewById(R.id.user_name);
        user_phone = (TextView) view.findViewById(R.id.user_phone);
        user_address = (TextView) view.findViewById(R.id.user_address);
        //  add_appartment = (TextView)view.findViewById(R.id.add_appartment);
        //   add_supervisor = (TextView)view.findViewById(R.id.add_supervisor);
        save_changes_profile = (Button) view.findViewById(R.id.save_changes_profile);
        info_box = (LinearLayout) view.findViewById(R.id.info_box);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);

        progressDialog = ProgressDialog.show(getActivity(), "", "Loading Data..", true, false);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        add_service = (ImageView) view.findViewById(R.id.add_service);
        add_vehicle = (ImageView) view.findViewById(R.id.add_vehicle);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Profile");
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        rootStorageRef = FirebaseStorage.getInstance().getReference();
        folderRef = rootStorageRef.child("user_profile_images");
        TextArrayList = new ArrayList<>();


//        for (int i = 0; i < vehicle_type.length; i++) {
//            flexBoxLayout_vehicle_type.addView(edittext(i, null, vehicle_type[i]));
//        }
        FirebaseHandler.getInstance().getAdd_vehicle_type()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            flexBoxLayout_vehicle_type.removeAllViews();
                            if (dataSnapshot.getValue() != null) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    Vehicle_TypeObject owner_services = data.getValue(Vehicle_TypeObject.class);
                                    flexBoxLayout_vehicle_type.addView(edittext(i, null, owner_services.getVehicle_type()));
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        FirebaseHandler.getInstance().getAdd_owner_serivces()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            flexBoxLayout_service.removeAllViews();
                            if (dataSnapshot.getValue() != null) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    Owner_Services owner_services = data.getValue(Owner_Services.class);
                                    flexBoxLayout_service.addView(edittext(i, null, owner_services.getService_title()));
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        //for (int i = 0; i < servie_flex.length; i++) {

        //  }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 1888) {
                //for camera
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(data.getData(), projection, null, null, null);
                int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                imgPath = cursor.getString(column_index_data);
                cursor.close();

                if (Build.VERSION.SDK_INT <= 19) {
                    handleCrop(resultCode, data, mCapturedImageURI);
                } else {
                    if (data == null) {
                        String[] projectionn = {
                                MediaStore.Images.Thumbnails._ID,  // The columns we want
                                MediaStore.Images.Thumbnails.IMAGE_ID,
                                MediaStore.Images.Thumbnails.KIND,
                                MediaStore.Images.Thumbnails.DATA};
                        String selection = MediaStore.Images.Thumbnails.KIND + "=" + // Select only mini's
                                MediaStore.Images.Thumbnails.MINI_KIND;

                        String sort = MediaStore.Images.Thumbnails._ID + " DESC";

//At the moment, this is a bit of a hack, as I'm returning ALL images, and just taking the latest one. There is a better way to narrow this down I think with a WHERE clause which is currently the selection variable
                        Cursor myCursor = getActivity().managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projectionn, selection, null, sort);

                        long imageId = 0l;
                        long thumbnailImageId = 0l;
                        String thumbnailPath = "";

                        try {
                            myCursor.moveToFirst();
                            imageId = myCursor.getLong(myCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID));
                            thumbnailImageId = myCursor.getLong(myCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID));
                            thumbnailPath = myCursor.getString(myCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
                        } finally {
                            myCursor.close();
                        }

                        //Create new Cursor to obtain the file Path for the large image

                        String[] largeFileProjection = {
                                MediaStore.Images.ImageColumns._ID,
                                MediaStore.Images.ImageColumns.DATA
                        };

                        String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
                        myCursor = getActivity().managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, largeFileProjection, null, null, largeFileSort);
                        String largeImagePath = "";

                        try {
                            myCursor.moveToFirst();

//This will actually give yo uthe file path location of the image.
                            largeImagePath = myCursor.getString(myCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
                        } finally {
                            myCursor.close();
                        }
                        // These are the two URI's you'll be interested in. They give you a handle to the actual images
                        Uri uriLargeImage = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(imageId));
                        Uri uriThumbnailImage = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, String.valueOf(thumbnailImageId));
                        beginCrop(uriLargeImage);
                    } else {
                        beginCrop(data.getData());
                    }
                }

            } else if (requestCode == 9162) {
                //for gallery
                //   beginCrop(data.getData());

                mCapturedImageURI = data.getData();
                String[] imgHolder = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(mCapturedImageURI, imgHolder, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(imgHolder[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();


                if (Build.VERSION.SDK_INT > 19) {
                    beginCrop(data.getData());
                } else {
                    handleCrop(resultCode, data, mCapturedImageURI);
                }
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data, mCapturedImageURI);
            }

        } else {
            Toast.makeText(getActivity(), "Nothing Selected !", Toast.LENGTH_LONG).show();
        }


    }

    private void uploadImage(ImageView imgPath) {
        try {
            //   File fileRef = new File(imgPath);


            final Date date = new Date(System.currentTimeMillis());
            //   final String filenew = fileRef.getName();
            //    AppLogs.d("fileNewName", filenew);
            //    int dot = filenew.lastIndexOf('.');
            //     String base = (dot == -1) ? filenew : filenew.substring(0, dot);
            //     final String extension = (dot == -1) ? "" : filenew.substring(dot + 1);
            //     AppLogs.d("extensionsss", extension);
            mProgressDialog = ProgressDialog.show(getActivity(), "Uploading Image", "loading...", true, false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            UploadTask uploadTask;
            // Uri file = Uri.fromFile(new File(imgPath));
            imageRef = folderRef.child(String.valueOf(date) + ".png");

            imgPath.setDrawingCacheEnabled(true);
            imgPath.buildDrawingCache();
            Bitmap b = imgPath.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            //  UploadTask uploadTask = ref.child(id + ".png").putBytes(bytes);


            uploadTask = imageRef.putBytes(bytes);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getActivity(), "UPLOAD FAILD", Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    Log.e("Image ka URL", "" + downloadUrl);
                    downloadURL = downloadUrl;
                    mProgressDialog.dismiss();
                    FirebaseHandler.getInstance().getUsersRef().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imageUrl").setValue(downloadUrl);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Uri beginCrop(Uri source) {

        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity(), Owner_Profile_Edit.this);
//        Crop.of(source,destination).withMaxSize(100,100).start(getActivity(), TabFragment1.this);

        return destination;
    }

    private void handleCrop(int resultCode, final Intent result, Uri mCapturedImageURI) {

        // imgPath = null;
        if (resultCode == getActivity().RESULT_OK) {
            Bitmap bitmap = null;
            try {
                if (Build.VERSION.SDK_INT <= 19) {
                    //     bitmap =(Bitmap) mCapturedImageURI.
                    //   InputStream image_stream =getActivity().getContentResolver().openInputStream(mCapturedImageURI);

                    //   bitmap= BitmapFactory.decodeStream(image_stream);
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mCapturedImageURI);

                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Crop.getOutput(result));
                }

                profile_pic_owner.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("param", mCapturedImageURI);
        outState.putParcelable("paramm", intent);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mCapturedImageURI = savedInstanceState.getParcelable("param");
            intent = savedInstanceState.getParcelable("paramm");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //   populateAutoComplete();
            }
        } else {
            Toast.makeText(getActivity(), "Please Allow Storage ..", Toast.LENGTH_SHORT).show();
        }

    }

    private TextView edittext(int id, Apartment hint, String text) {
        final TextView textView = new TextView(AppController.getContext());
        //    EditText editText = new EditText(getActivity());
        textView.setId(id);
        //    textView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //   textView.setHeight(45);
        textView.setBackgroundResource(R.drawable.curver_text_blue);
        textView.setTag(hint);
        //   textView.setHint(hint);
        //  editText.setFocusable(focus);
        textView.setText(text);
        //   textView.setMaxLines(1);
        //   textView.setPadding(25, 0, 25, 0);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setAllCaps(true);
        textView.setTextColor(Color.WHITE);
        //   textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.add_capsule_icon, 0, 0, 0);
        //    textView.setCompoundDrawablePadding(5);

        //  editText.setHintTextColor(Color.LTGRAY);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

      //  int marginInDp = (int) TypedValue.applyDimension(
       //         TypedValue.COMPLEX_UNIT_DIP, 8, getActivity().getResources()
     //                   .getDisplayMetrics());// 8 is margin in dp
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 10, 10);
        //bottom/left/right/top
        textView.setLayoutParams(params);

        // textInputLayout.addView(editText);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textView.getTag() != null) {
                    Appartment_Detail appartment_detail = new Appartment_Detail();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("object", (Parcelable) textView.getTag());
                    appartment_detail.setArguments(bundle);
                    getActivity().getSupportFragmentManager().popBackStack();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .replace(R.id.profile_container, appartment_detail).commit();
                }
            }
        });
        // TextArrayList.add(editText);

        return textView;
    }

    private TextView Supertext(int id, Supervisor hint, String text) {
        final TextView textView = new TextView(AppController.getContext());
        //    EditText editText = new EditText(getActivity());
        textView.setId(id);
        //    textView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //   textView.setHeight(45);
        textView.setBackgroundResource(R.drawable.curver_text_blue);
        textView.setTag(hint);
        //   textView.setHint(hint);
        //  editText.setFocusable(focus);
        textView.setText(text);
        //   textView.setMaxLines(1);
        //  textView.setPadding(25, 0, 25, 0);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setAllCaps(true);
        textView.setTextColor(Color.WHITE);
        //   textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.add_capsule_icon, 0, 0, 0);
        //    textView.setCompoundDrawablePadding(5);

        //  editText.setHintTextColor(Color.LTGRAY);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

     //   int marginInDp = (int) TypedValue.applyDimension(
     ////           TypedValue.COMPLEX_UNIT_DIP, 8, AppController.getContext().getResources()
      //                  .getDisplayMetrics());// 8 is margin in dp
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 10, 10);
        //bottom/left/right/top
        textView.setLayoutParams(params);

        // textInputLayout.addView(editText);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Supervisor_Profile supervisor_profile = new Supervisor_Profile();
                Bundle bundle = new Bundle();
                bundle.putParcelable("object", (Parcelable) textView.getTag());
                supervisor_profile.setArguments(bundle);
                getActivity().getSupportFragmentManager().popBackStack();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.profile_container, supervisor_profile).commit();
            }
        });
        // TextArrayList.add(editText);

        return textView;
    }


    private TextView Optionedittext(int id, String hint, String text) {
        TextView textView = new TextView(AppController.getContext());
        //    EditText editText = new EditText(getActivity());
        textView.setId(id);
        //    textView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //   textView.setHeight(45);
        textView.setBackgroundResource(R.drawable.curver_text_gray);

        //   textView.setHint(hint);
        //  editText.setFocusable(focus);
        textView.setText(text);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setAllCaps(true);
        //   textView.setMaxLines(1);
        textView.setPadding(25, 10, 25, 10);
        textView.setTextColor(Color.WHITE);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.add_capsule_icon, 0, 0, 0);
        textView.setCompoundDrawablePadding(5);

        //  editText.setHintTextColor(Color.LTGRAY);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        int marginInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, getActivity().getResources()
                        .getDisplayMetrics());// 8 is margin in dp
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 10, 10);
        //bottom/left/right/top
        textView.setLayoutParams(params);

        // textInputLayout.addView(editText);

        // TextArrayList.add(editText);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.profile_container, new Add_Appartment()).commit();
            }
        });

        return textView;
    }

    private TextView superVisorText(int id, String hint, String text) {
        TextView textView = new TextView(AppController.getContext());
        //    EditText editText = new EditText(getActivity());
        textView.setId(id);
        //    textView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //   textView.setHeight(45);
        textView.setBackgroundResource(R.drawable.curver_text_gray);

        //   textView.setHint(hint);
        //  editText.setFocusable(focus);
        textView.setText(text);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setAllCaps(true);
        //   textView.setMaxLines(1);
        textView.setPadding(25, 10, 25, 10);
        textView.setTextColor(Color.WHITE);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.add_capsule_icon, 0, 0, 0);
        textView.setCompoundDrawablePadding(5);

        //  editText.setHintTextColor(Color.LTGRAY);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        //  int marginInDp = (int) TypedValue.applyDimension(
        //     TypedValue.COMPLEX_UNIT_DIP, 8, getActivity().getResources()
        //              .getDisplayMetrics());// 8 is margin in dp
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 10, 10);
        //bottom/left/right/top
        textView.setLayoutParams(params);

        // textInputLayout.addView(editText);

        // TextArrayList.add(editText);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.profile_container, new Add_Supervisor_Boy()).commit();
            }
        });

        return textView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        parent = (ProfileActivity) activity;
    }
    @Override
    public void onResume() {
        super.onResume();

    }
}
