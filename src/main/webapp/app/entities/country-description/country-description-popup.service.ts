import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CountryDescription } from './country-description.model';
import { CountryDescriptionService } from './country-description.service';
@Injectable()
export class CountryDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private countryDescriptionService: CountryDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.countryDescriptionService.find(id).subscribe((countryDescription) => {
                this.countryDescriptionModalRef(component, countryDescription);
            });
        } else {
            return this.countryDescriptionModalRef(component, new CountryDescription());
        }
    }

    countryDescriptionModalRef(component: Component, countryDescription: CountryDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.countryDescription = countryDescription;
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
