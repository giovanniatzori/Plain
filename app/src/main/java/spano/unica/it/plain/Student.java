package spano.unica.it.plain;
import java.util.Objects;
/**
 * Created by giovy on 14/07/2017.
 */

public class Student {

        private Integer id;
        private String name;
        private String surname;
        private StickerList stickerList = new StickerList();
        private String img;

        public Student(){}

        public Student(Integer id){
            this.id = id;
        };

        public Student(String name, String surname){
            this.name = name;
            this.surname = surname;
        };

        public Student(Integer id, String name, String surname, String img){
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.img = img;
        }

        public boolean equals (Student s){
            return Objects.equals(this.id, s.getId());
        }

        @Override
        public String toString(){
            return this.id + ", " + this.name + " " + this.surname + ": " + this.stickerList;
        }


        /**
         * @return the id
         */
        public Integer getId() {
            return id;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the surname
         */
        public String getSurname() {
            return surname;
        }

        /**
         * @param id the id to set
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @param surname the surname to set
         */
        public void setSurname(String surname) {
            this.surname = surname;
        }

        /**
         * @return the stickerList
         */
        public StickerList getStickerList() {
            return stickerList;
        }

        /**
         * @param stickerList the stickerList to set
         */
        public void setStickerList(StickerList stickerList) {
            this.stickerList = stickerList;
        }

        /**
         * @return the img
         */
        public String getImg() {
            return img;
        }

        /**
         * @param img the img to set
         */
        public void setImg(String img) {
            this.img = img;
        }

}

