package ru.ase.ims.enomanager.entity;

import lombok.Getter;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.Release;
import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;

public class EnoviaEntityBuilder {

    public static EnoviaEntityBuilderEx getEnoviaEntityBuilder() {
        return new EnoviaEntityBuilderEx();
    }

    public static class EnoviaEntityBuilderEx {

        @Getter
        private EnoviaEntity enoviaEntity;

        EnoviaEntityBuilderEx(){
            enoviaEntity = new EnoviaEntity();
        }

        public EnoviaEntityBuilderEx id(Long id){
            this.enoviaEntity.setId(id);
            return this;
        }

        public EnoviaEntityBuilderEx entityName(String entityName){
            this.enoviaEntity.setEntityName(entityName);
            return this;
        }

        public EnoviaEntityBuilderEx release(Release release){
            this.enoviaEntity.setRelease(release);
            return this;
        }

        public EnoviaEntityBuilderEx description(String description){
            this.enoviaEntity.setDescription(description);
            return this;
        }

        public EnoviaEntityBuilderEx ematrix(Ematrix ematrix){
            this.enoviaEntity.setEmatrix(ematrix);
            return this;
        }

        public EnoviaEntityBuilderEx fileName(String fileName){
            this.enoviaEntity.setFileName(fileName);
            return this;
        }
        public EnoviaEntityBuilderEx type(String type){
            this.enoviaEntity.setType(type);
            return this;
        }
    }
}
