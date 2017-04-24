import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductOption } from './product-option.model';
import { ProductOptionService } from './product-option.service';
@Injectable()
export class ProductOptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productOptionService: ProductOptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productOptionService.find(id).subscribe((productOption) => {
                if (productOption.created_date) {
                    productOption.created_date = {
                        year: productOption.created_date.getFullYear(),
                        month: productOption.created_date.getMonth() + 1,
                        day: productOption.created_date.getDate()
                    };
                }
                if (productOption.last_modified_date) {
                    productOption.last_modified_date = {
                        year: productOption.last_modified_date.getFullYear(),
                        month: productOption.last_modified_date.getMonth() + 1,
                        day: productOption.last_modified_date.getDate()
                    };
                }
                this.productOptionModalRef(component, productOption);
            });
        } else {
            return this.productOptionModalRef(component, new ProductOption());
        }
    }

    productOptionModalRef(component: Component, productOption: ProductOption): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productOption = productOption;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
