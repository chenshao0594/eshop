import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GeoZone } from './geo-zone.model';
import { GeoZoneService } from './geo-zone.service';
@Injectable()
export class GeoZonePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private geoZoneService: GeoZoneService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.geoZoneService.find(id).subscribe((geoZone) => {
                this.geoZoneModalRef(component, geoZone);
            });
        } else {
            return this.geoZoneModalRef(component, new GeoZone());
        }
    }

    geoZoneModalRef(component: Component, geoZone: GeoZone): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.geoZone = geoZone;
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
