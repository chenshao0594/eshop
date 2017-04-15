import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { GeoZone } from './geo-zone.model';
import { GeoZonePopupService } from './geo-zone-popup.service';
import { GeoZoneService } from './geo-zone.service';

@Component({
    selector: 'jhi-geo-zone-dialog',
    templateUrl: './geo-zone-dialog.component.html'
})
export class GeoZoneDialogComponent implements OnInit {

    geoZone: GeoZone;
    authorities: any[];
    isSaving: boolean;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private geoZoneService: GeoZoneService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['geoZone']);
        this.geoZone = new GeoZone();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.geoZone.id !== undefined) {
            this.geoZoneService.update(this.geoZone)
                .subscribe((res: GeoZone) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.geoZoneService.create(this.geoZone)
                .subscribe((res: GeoZone) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: GeoZone) {
        this.eventManager.broadcast({ name: 'geoZoneListModification', content: 'OK'});
        this.isSaving = false;
        this.geoZone = result;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-geo-zone-popup',
    template: ''
})
export class GeoZonePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private geoZonePopupService: GeoZonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.geoZonePopupService
                    .open(GeoZoneDialogComponent, params['id']);
            } else {
                this.modalRef = this.geoZonePopupService
                    .open(GeoZoneDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
