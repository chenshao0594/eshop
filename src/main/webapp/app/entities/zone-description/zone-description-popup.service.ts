import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ZoneDescription } from './zone-description.model';
import { ZoneDescriptionService } from './zone-description.service';
@Injectable()
export class ZoneDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private zoneDescriptionService: ZoneDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.zoneDescriptionService.find(id).subscribe((zoneDescription) => {
                this.zoneDescriptionModalRef(component, zoneDescription);
            });
        } else {
            return this.zoneDescriptionModalRef(component, new ZoneDescription());
        }
    }

    zoneDescriptionModalRef(component: Component, zoneDescription: ZoneDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.zoneDescription = zoneDescription;
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
