import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ManufacturerDescription } from './manufacturer-description.model';
import { ManufacturerDescriptionService } from './manufacturer-description.service';
@Injectable()
export class ManufacturerDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private manufacturerDescriptionService: ManufacturerDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.manufacturerDescriptionService.find(id).subscribe((manufacturerDescription) => {
                if (manufacturerDescription.dateLastClick) {
                    manufacturerDescription.dateLastClick = {
                        year: manufacturerDescription.dateLastClick.getFullYear(),
                        month: manufacturerDescription.dateLastClick.getMonth() + 1,
                        day: manufacturerDescription.dateLastClick.getDate()
                    };
                }
                this.manufacturerDescriptionModalRef(component, manufacturerDescription);
            });
        } else {
            return this.manufacturerDescriptionModalRef(component, new ManufacturerDescription());
        }
    }

    manufacturerDescriptionModalRef(component: Component, manufacturerDescription: ManufacturerDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.manufacturerDescription = manufacturerDescription;
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
