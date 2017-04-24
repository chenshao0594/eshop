import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TaxClass } from './tax-class.model';
import { TaxClassService } from './tax-class.service';
@Injectable()
export class TaxClassPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private taxClassService: TaxClassService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.taxClassService.find(id).subscribe((taxClass) => {
                if (taxClass.created_date) {
                    taxClass.created_date = {
                        year: taxClass.created_date.getFullYear(),
                        month: taxClass.created_date.getMonth() + 1,
                        day: taxClass.created_date.getDate()
                    };
                }
                if (taxClass.last_modified_date) {
                    taxClass.last_modified_date = {
                        year: taxClass.last_modified_date.getFullYear(),
                        month: taxClass.last_modified_date.getMonth() + 1,
                        day: taxClass.last_modified_date.getDate()
                    };
                }
                this.taxClassModalRef(component, taxClass);
            });
        } else {
            return this.taxClassModalRef(component, new TaxClass());
        }
    }

    taxClassModalRef(component: Component, taxClass: TaxClass): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.taxClass = taxClass;
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
