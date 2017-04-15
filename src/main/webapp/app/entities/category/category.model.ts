import { CategoryDescription } from '../category-description';
export class Category {
    constructor(
        public id?: number,
        public depth?: number,
        public sortOrder?: number,
        public categoryStatus?: boolean,
        public lineage?: string,
        public visible?: boolean,
        public code?: string,
        public categoryImage?: string,
        public categories?: Category,
        public descriptions?: CategoryDescription,
        public parent?: Category,
    ) {
        this.categoryStatus = false;
        this.visible = false;
    }
}
