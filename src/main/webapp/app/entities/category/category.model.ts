import { CategoryDescription } from '../category-description';
import { MerchantStore } from '../merchant-store';
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
        public merchantStore?: MerchantStore,
        public parent?: Category,
    ) {
        this.categoryStatus = true;
        this.visible = true;
    }
}
