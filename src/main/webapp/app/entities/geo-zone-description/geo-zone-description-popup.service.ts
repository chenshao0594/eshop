import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GeoZoneDescription } from './geo-zone-description.model';
import { GeoZoneDescriptionService } from './geo-zone-description.service';
@Injectable()
export class GeoZoneDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private geoZoneDescriptionService: GeoZoneDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.geoZoneDescriptionService.find(id).subscribe((geoZoneDescription) => {
                this.geoZoneDescriptionModalRef(component, geoZoneDescription);
            });
        } else {
            return this.geoZoneDescriptionModalRef(component, new GeoZoneDescription());
        }
    }

    geoZoneDescriptionModalRef(component: Component, geoZoneDescription: GeoZoneDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.geoZoneDescription = geoZoneDescription;
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
