import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { GeoZoneDescription } from './geo-zone-description.model';
import { GeoZoneDescriptionPopupService } from './geo-zone-description-popup.service';
import { GeoZoneDescriptionService } from './geo-zone-description.service';
import { GeoZone, GeoZoneService } from '../geo-zone';
import { Language, LanguageService } from '../language';

@Component({
    selector: 'jhi-geo-zone-description-dialog',
    templateUrl: './geo-zone-description-dialog.component.html'
})
export class GeoZoneDescriptionDialogComponent implements OnInit {

    geoZoneDescription: GeoZoneDescription;
    authorities: any[];
    isSaving: boolean;

    geozones: GeoZone[];

    languages: Language[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private geoZoneDescriptionService: GeoZoneDescriptionService,
        private geoZoneService: GeoZoneService,
        private languageService: LanguageService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['geoZoneDescription']);
        this.geoZoneDescription = new GeoZoneDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.geoZoneService.query().subscribe(
            (res: Response) => { this.geozones = res.json(); }, (res: Response) => this.onError(res.json()));
        this.languageService.query().subscribe(
            (res: Response) => { this.languages = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.geoZoneDescription.id !== undefined) {
            this.geoZoneDescriptionService.update(this.geoZoneDescription)
                .subscribe((res: GeoZoneDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.geoZoneDescriptionService.create(this.geoZoneDescription)
                .subscribe((res: GeoZoneDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: GeoZoneDescription) {
        this.eventManager.broadcast({ name: 'geoZoneDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.geoZoneDescription = result;
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

    trackGeoZoneById(index: number, item: GeoZone) {
        return item.id;
    }

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-geo-zone-description-popup',
    template: ''
})
export class GeoZoneDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private geoZoneDescriptionPopupService: GeoZoneDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.geoZoneDescriptionPopupService
                    .open(GeoZoneDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.geoZoneDescriptionPopupService
                    .open(GeoZoneDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
