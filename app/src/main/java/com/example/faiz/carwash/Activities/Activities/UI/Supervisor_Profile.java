package com.example.faiz.carwash.Activities.Activities.UI;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
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
import android.os.Environment;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faiz.carwash.Activities.Activities.Model.Supervisor;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AST on 9/15/2017.
 */

public class Supervisor_Profile extends android.support.v4.app.Fragment {

    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public Supervisor supervisor;
    public TextView supervisor_name, supervisor_address, supervisor_phone;

    public ImageButton floatingActionButton;
    public ImageView floatingMenuItem1;
    public ImageView floatingMenuItem2;
    public FlexboxLayout flexBoxLayout_appartment, flexBoxLayout_block, flexBoxLayout_roles;
    public CircleImageView supervisor_profile;
    private static final int SELECTED_PICTURE = 9162;
    private static final int CAMERA_REQUEST = 1888;
    public Uri mCapturedImageURI;
    private static final int REQUEST_READ_CONTACTS = 2;
    private StorageReference rootStorageRef, imageRef, folderRef, fileStorageRef;
    private ProgressDialog mProgressDialog;
    private String imgPath;
    private String downloadURL="";
    public Button done_super_profile;
    public TextView user_role;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.supervisor_profile, null);
        intializeView(view);

        if (getArguments() != null) {
            supervisor = getArguments().getParcelable("object");
            supervisor_name.setText(supervisor.getSuperVisor_name());
            supervisor_address.setText(supervisor.getSuperVisor_address());
            supervisor_phone.setText(supervisor.getSuperVisor_mobile());
            if(supervisor.getSuperVisor_type()==0){
                user_role.setText("BOY");
            }else if(supervisor.getSuperVisor_type()==1){
                user_role.setText("CHIEF SUPERVISOR");
            }else if(supervisor.getSuperVisor_type()==2){
                user_role.setText("SUPERVISOR");
            }
            flexBoxLayout_appartment.addView(textApartment(1, supervisor, supervisor.getSuperVisor_apartment()));
            flexBoxLayout_block.addView(textblocks(1, supervisor, supervisor.getSuperVisor_block()));
            flexBoxLayout_roles.addView(textRole(1, supervisor,user_role.getText().toString()));
            Glide.with(getActivity()).load(supervisor.getSuper_visorImgUrl()).asBitmap().placeholder(R.drawable.user).into(supervisor_profile);
        }

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAnimations();
            }
        });

        floatingMenuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                animations();
                customAnimations();

            }
        });
        floatingMenuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                animations();
                //       customAnimations();
                Add_Supervisor_Boy appartment_detail = new Add_Supervisor_Boy();
                Bundle bundle  = new Bundle();
                bundle.putParcelable("object",supervisor);
                appartment_detail.setArguments(bundle);
                getFragmentManager().popBackStack();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.profile_container,appartment_detail).commit();
            }
        });

        done_super_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        supervisor_profile.setOnClickListener(new View.OnClickListener() {
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
                            if (Build.VERSION.SDK_INT > 20) {
                                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Images");
                                imagesFolder.mkdirs();
                                File image = new File(imagesFolder.getPath(), "MyImage_.jpg");
                                String fileName = "temp.jpg";
                                ContentValues values = new ContentValues();
                                values.put(MediaStore.Images.Media.TITLE, image.getAbsolutePath());
                                mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            }


                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
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

        return view;
    }

    public void customAnimations() {
        if (floatingMenuItem1.getVisibility() == View.VISIBLE) {
            implementOnCloseAnimations();
        } else {
            implentOnOpenAnimations();
        }

    }

    private void implentOnOpenAnimations() {
        ObjectAnimator slideInAnimation = ObjectAnimator.ofFloat(floatingMenuItem1, "translationY", 200, 0);
        slideInAnimation.setDuration(200);
        ObjectAnimator fadeInAnimatin = ObjectAnimator.ofFloat(floatingMenuItem1, "alpha", 0, 1);
        fadeInAnimatin.setDuration(200);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleX", 1f);
        scaleAnimationX.setDuration(200);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleY", 1f);
        scaleAnimationY.setDuration(200);

        ObjectAnimator slideInAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "translationY", 200, 0);
        slideInAnimation2.setDuration(200);
        ObjectAnimator fadeInAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "alpha", 0, 1);
        fadeInAnimation2.setDuration(200);
        ObjectAnimator scaleAnimation2X = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleX", 1f);
        scaleAnimation2X.setDuration(200);
        ObjectAnimator scaleAnimation2Y = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleY", 1f);
        scaleAnimation2Y.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(slideInAnimation).with(fadeInAnimatin).with(scaleAnimationX).with(scaleAnimationY).with(slideInAnimation2).with(fadeInAnimation2).with(scaleAnimation2X).with(scaleAnimation2Y);


        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                floatingMenuItem1.setVisibility(View.VISIBLE);
                floatingMenuItem2.setVisibility(View.VISIBLE);
                //    clist_Back_view.setVisibility(View.VISIBLE);
                floatingActionButton.setBackgroundResource(R.mipmap.closed);

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();
    }

    private void implementOnCloseAnimations() {
        ObjectAnimator slideOutAnimation = ObjectAnimator.ofFloat(floatingMenuItem1, "translationY", 0, 200);
        slideOutAnimation.setDuration(200);
        ObjectAnimator fadeOutAnimation = ObjectAnimator.ofFloat(floatingMenuItem1, "alpha", 1, 0);
        fadeOutAnimation.setDuration(200);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleX", 0.5f);
        scaleAnimationX.setDuration(200);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleY", 0.5f);
        scaleAnimationY.setDuration(200);

        ObjectAnimator slideOutAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "translationY", 0, 200);
        slideOutAnimation2.setDuration(200);
        ObjectAnimator fadeOutAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "alpha", 1, 0);
        fadeOutAnimation2.setDuration(200);
        ObjectAnimator scaleAnimation2X = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleX", 0.5f);
        scaleAnimation2X.setDuration(200);
        ObjectAnimator scaleAnimation2Y = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleY", 0.5f);
        scaleAnimation2Y.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(slideOutAnimation).with(fadeOutAnimation).with(scaleAnimationX).with(scaleAnimationY).with(slideOutAnimation2).with(fadeOutAnimation2).with(scaleAnimation2X).with(scaleAnimation2Y);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                floatingMenuItem1.setVisibility(View.GONE);
                floatingMenuItem2.setVisibility(View.GONE);
                //  clist_Back_view.setVisibility(View.GONE);
                floatingActionButton.setBackgroundResource(R.mipmap.sort_btn);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 1888) {
                //for camera
//

                if (Build.VERSION.SDK_INT <= 19) {
                    handleCrop(resultCode, data, mCapturedImageURI);
                } else {
                    if (data == null) {
                        String[] projectionn = {
//                                MediaStore.Images.Thumbnails._ID,  // The columns we want
//                                MediaStore.Images.Thumbnails.IMAGE_ID,
//                                MediaStore.Images.Thumbnails.KIND,
                                MediaStore.Images.Thumbnails.DATA};
                        String selection = MediaStore.Images.Thumbnails.KIND + "=" + // Select only mini's
                                MediaStore.Images.Thumbnails.MINI_KIND;

                        String sort = MediaStore.Images.Thumbnails._ID + " DESC";

//At the moment, this is a bit of a hack, as I'm returning ALL images, and just taking the latest one. There is a better way to narrow this down I think with a WHERE clause which is currently the selection variable
                        Cursor myCursor = getActivity().getContentResolver().query(mCapturedImageURI, projectionn, null, null, null);

                        long imageId = 0l;
                        long thumbnailImageId = 0l;
                        String thumbnailPath = "";

                        try {
                            myCursor.moveToFirst();

                        } finally {
                            myCursor.close();
                        }

                        beginCrop(mCapturedImageURI);
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


        super.onActivityResult(requestCode, resultCode, data);
    }

    private Uri beginCrop(Uri source) {

        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity(), Supervisor_Profile.this);
//        Crop.of(source,destination).withMaxSize(100,100).start(getActivity(), TabFragment1.this);
        return destination;
    }

    private void handleCrop(int resultCode, final Intent result, Uri mCapturedImageURI) {

        // imgPath = null;
        if (resultCode == getActivity().RESULT_OK) {
            Bitmap bitmap = null;
            try {
                if (Build.VERSION.SDK_INT <= 19) {

                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mCapturedImageURI);

                    Glide.with(getActivity()).load(mCapturedImageURI).asBitmap().into(supervisor_profile);

                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Crop.getOutput(result));
                    Glide.with(getActivity()).load(Crop.getOutput(result)).asBitmap().into(supervisor_profile);
                }

             //   supervisor_profile.setImageBitmap(bitmap);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Want to Upload Image or not ?");
                // uploadImage(profile_pic_owner);
                builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        uploadImage(supervisor_profile);

                    }
                });
                builder.setNeutralButton("Cancel", null);
                builder.create().show();



            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
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
                    mProgressDialog.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    Log.e("Image ka URL", "" + downloadUrl);
                    downloadURL = downloadUrl;
                    mProgressDialog.dismiss();

                    FirebaseHandler.getInstance().getAdd_supervisor().child(supervisor.getSuperVisor_id()).child("super_visorImgUrl").setValue(downloadUrl);



                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    private TextView textApartment(int id, Supervisor hint, String text) {
        TextView textView = new TextView(getActivity());
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
        textView.setPadding(25, 0, 25, 0);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setAllCaps(true);
        textView.setTextColor(Color.WHITE);
        //   textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.add_capsule_icon, 0, 0, 0);
        //    textView.setCompoundDrawablePadding(5);

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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                        .addToBackStack(null)
//                        .replace(R.id.profile_container, new Supervisor_profile_Details()).commit();
            }
        });
        // TextArrayList.add(editText);

        return textView;
    }

    private TextView textRole(int id, Supervisor hint, String text) {
        TextView textView = new TextView(getActivity());
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
        textView.setPadding(25, 0, 25, 0);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setAllCaps(true);
        textView.setTextColor(Color.WHITE);
        //   textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.add_capsule_icon, 0, 0, 0);
        //    textView.setCompoundDrawablePadding(5);

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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                        .addToBackStack(null)
//                        .replace(R.id.profile_container, new Supervisor_profile_Details()).commit();
            }
        });
        // TextArrayList.add(editText);

        return textView;
    }

    private TextView textblocks(int id, Supervisor hint, String text) {
        TextView textView = new TextView(getActivity());
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
        textView.setPadding(25, 0, 25, 0);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setAllCaps(true);
        textView.setTextColor(Color.WHITE);
        //   textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.add_capsule_icon, 0, 0, 0);
        //    textView.setCompoundDrawablePadding(5);

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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                        .addToBackStack(null)
//                        .replace(R.id.profile_container, new Supervisor_profile_Details()).commit();
            }
        });
        // TextArrayList.add(editText);

        return textView;
    }

    private void intializeView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Profile");
        floatingActionButton = (ImageButton) view.findViewById(R.id.customer_list_sort);
        floatingMenuItem1 = (ImageView) view.findViewById(R.id.floatingMenuItem1);
        floatingMenuItem2 = (ImageView) view.findViewById(R.id.floatingMenuItem2);
        supervisor_name = (TextView) view.findViewById(R.id.supervisor_name);
        supervisor_address = (TextView) view.findViewById(R.id.supervisor_address);
        supervisor_phone = (TextView) view.findViewById(R.id.supervisor_phone);
        user_role = (TextView)view.findViewById(R.id.user_role);
        done_super_profile = (Button)view.findViewById(R.id.done_super_profile);
        flexBoxLayout_appartment = (FlexboxLayout) view.findViewById(R.id.flexBoxLayout_appartment);
        flexBoxLayout_block = (FlexboxLayout) view.findViewById(R.id.flexBoxLayout_block);
        flexBoxLayout_roles = (FlexboxLayout) view.findViewById(R.id.flexBoxLayout_roles);
        supervisor_profile = (CircleImageView) view.findViewById(R.id.supervisor_profile);
        rootStorageRef = FirebaseStorage.getInstance().getReference();
        folderRef = rootStorageRef.child("supervisor_Images");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //   populateAutoComplete();
            }
        } else {
            Toast.makeText(getActivity(), "Please Allow Storage ..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
    @Override
    public void onResume() {
        super.onResume();

    }
}
