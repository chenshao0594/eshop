import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Zone } from './zone.model';
import { ZoneService } from './zone.service';
@Injectable()
export class ZonePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private zoneService: ZoneService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.zoneService.find(id).subscribe((zone) => {
                if (zone.createdDate) {
                    zone.createdDate = {
                        year: zone.createdDate.getFullYear(),
                        month: zone.createdDate.getMonth() + 1,
                        day: zone.createdDate.getDate()
                    };
                }
                if (zone.lastModifiedDate) {
                    zone.lastModifiedDate = {
                        year: zone.lastModifiedDate.getFullYear(),
                        month: zone.lastModifiedDate.getMonth() + 1,
                        day: zone.lastModifiedDate.getDate()
                    };
                }
                this.zoneModalRef(component, zone);
            });
        } else {
            return this.zoneModalRef(component, new Zone());
        }
    }

    zoneModalRef(component: Component, zone: Zone): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.zone = zone;
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
