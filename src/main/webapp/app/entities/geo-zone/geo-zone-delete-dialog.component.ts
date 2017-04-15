import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { GeoZone } from './geo-zone.model';
import { GeoZonePopupService } from './geo-zone-popup.service';
import { GeoZoneService } from './geo-zone.service';

@Component({
    selector: 'jhi-geo-zone-delete-dialog',
    templateUrl: './geo-zone-delete-dialog.component.html'
})
export class GeoZoneDeleteDialogComponent {

    geoZone: GeoZone;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private geoZoneService: GeoZoneService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['geoZone']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.geoZoneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'geoZoneListModification',
                content: 'Deleted an geoZone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-geo-zone-delete-popup',
    template: ''
})
export class GeoZoneDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private geoZonePopupService: GeoZonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.geoZonePopupService
                .open(GeoZoneDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
