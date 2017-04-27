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
                if (manufacturer.createdDate) {
                    manufacturer.createdDate = {
                        year: manufacturer.createdDate.getFullYear(),
                        month: manufacturer.createdDate.getMonth() + 1,
                        day: manufacturer.createdDate.getDate()
                    };
                }
                if (manufacturer.lastModifiedDate) {
                    manufacturer.lastModifiedDate = {
                        year: manufacturer.lastModifiedDate.getFullYear(),
                        month: manufacturer.lastModifiedDate.getMonth() + 1,
                        day: manufacturer.lastModifiedDate.getDate()
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
