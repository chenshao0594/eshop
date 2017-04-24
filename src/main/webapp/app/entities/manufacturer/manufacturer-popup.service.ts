import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Manufacturer } from './manufacturer.model';
import { ManufacturerService } from './manufacturer.service';
@Injectable()
export class ManufacturerPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private manufacturerService: ManufacturerService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.manufacturerService.find(id).subscribe((manufacturer) => {
                if (manufacturer.created_date) {
                    manufacturer.created_date = {
                        year: manufacturer.created_date.getFullYear(),
                        month: manufacturer.created_date.getMonth() + 1,
                        day: manufacturer.created_date.getDate()
                    };
                }
                if (manufacturer.last_modified_date) {
                    manufacturer.last_modified_date = {
                        year: manufacturer.last_modified_date.getFullYear(),
                        month: manufacturer.last_modified_date.getMonth() + 1,
                        day: manufacturer.last_modified_date.getDate()
                    };
                }
                this.manufacturerModalRef(component, manufacturer);
            });
        } else {
            return this.manufacturerModalRef(component, new Manufacturer());
        }
    }

    manufacturerModalRef(component: Component, manufacturer: Manufacturer): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.manufacturer = manufacturer;
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
