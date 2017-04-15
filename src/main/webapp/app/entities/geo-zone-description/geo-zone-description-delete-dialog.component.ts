import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { GeoZoneDescription } from './geo-zone-description.model';
import { GeoZoneDescriptionPopupService } from './geo-zone-description-popup.service';
import { GeoZoneDescriptionService } from './geo-zone-description.service';

@Component({
    selector: 'jhi-geo-zone-description-delete-dialog',
    templateUrl: './geo-zone-description-delete-dialog.component.html'
})
export class GeoZoneDescriptionDeleteDialogComponent {

    geoZoneDescription: GeoZoneDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private geoZoneDescriptionService: GeoZoneDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['geoZoneDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.geoZoneDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'geoZoneDescriptionListModification',
                content: 'Deleted an geoZoneDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-geo-zone-description-delete-popup',
    template: ''
})
export class GeoZoneDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private geoZoneDescriptionPopupService: GeoZoneDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.geoZoneDescriptionPopupService
                .open(GeoZoneDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
