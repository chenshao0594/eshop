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
                if (zone.created_date) {
                    zone.created_date = {
                        year: zone.created_date.getFullYear(),
                        month: zone.created_date.getMonth() + 1,
                        day: zone.created_date.getDate()
                    };
                }
                if (zone.last_modified_date) {
                    zone.last_modified_date = {
                        year: zone.last_modified_date.getFullYear(),
                        month: zone.last_modified_date.getMonth() + 1,
                        day: zone.last_modified_date.getDate()
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
