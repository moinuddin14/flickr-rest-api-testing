package khaja.config;

/**
 * Created by Khaja on 11/2/16.
 */

/**
 * All API End Points
 */
public class BasePath {

    public class Photos{

        public static final String ADD_TAGS="flickr.photos.addTags";
        public static final String DELETE="flickr.photos.delete";
        public static final String GET_ALL_CONTEXTS="flickr.photos.getAllContexts";
        public static final String GET_CONTACTS_PHOTOS="flickr.photos.getContactsPhotos";
        public static final String GET_CONTACTS_PUBLIC_PHOTOS="flickr.photos.getContactsPublicPhotos";
        public static final String GET_CONTEXT="flickr.photos.getContext";
        public static final String GET_COUNTS="flickr.photos.getCounts";
        public static final String GET_EXIF="flickr.photos.getExif";
        public static final String GET_FAVORITES="flickr.photos.getFavorites";
        public static final String GET_INFO="flickr.photos.getInfo";
        public static final String GET_NOT_INSET="flickr.photos.getNotInSet";
        public static final String GET_PERMS="flickr.photos.getPerms";
        public static final String GET_RECENT="flickr.photos.getRecent";
        public static final String GET_SIZES="flickr.photos.getSizes";
        public static final String GET_UNTAGGED="flickr.photos.getUntagged";
        public static final String GET_WITH_GEO_DATA="flickr.photos.getWithGeoData";
        public static final String GET_WITHOUT_GEO_DATA="flickr.photos.getWithoutGeoData";
        public static final String RECENTLY_UPDATED="flickr.photos.recentlyUpdated";
        public static final String REMOVE_TAG="flickr.photos.removeTag";
        public static final String SEARCH="flickr.photos.search";
        public static final String SET_CONTENT_TYPE="flickr.photos.setContentType";
        public static final String SET_DATES="flickr.photos.setDates";
        public static final String SET_META="flickr.photos.setMeta";
        public static final String SET_PERMS="flickr.photos.setPerms";
        public static final String SET_AFETY_LEVEL="flickr.photos.setSafetyLevel";
        public static final String SET_TAGS="flickr.photos.setTags";

        public class comments{
        /**
         * Photo Comments API
         * Flickr has implemented the end point as method, in twitter it will be like photos/comments/addcomment
         */

        public static final String ADD_COMMENT="flickr.photos.comments.addComment";
        public static final String DELETE_COMMENT="flickr.photos.comments.deleteComment";
        public static final String EDIT_COMMENT="flickr.photos.comments.editComment";
        public static final String GET_LIST_COMMENTS="flickr.photos.comments.getList";
        public static final String GET_RECENT_FOR_CONTACTS="";

        }

        public class people{
            public static final String ADD="flickr.photos.people.add";
            public static final String DELETE="flickr.photos.people.delete";
            public static final String DELETE_COORDS="flickr.photos.people.deleteCoords";
            public static final String EDIT_COORDS="flickr.photos.people.editCoords";
            public static final String GET_LIST="flickr.photos.people.getList";
        }
    }
}
