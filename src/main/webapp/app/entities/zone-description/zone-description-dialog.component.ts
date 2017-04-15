import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ZoneDescription } from './zone-description.model';
import { ZoneDescriptionPopupService } from './zone-description-popup.service';
import { ZoneDescriptionService } from './zone-description.service';
import { Zone, ZoneService } from '../zone';

@Component({
    selector: 'jhi-zone-description-dialog',
    templateUrl: './zone-description-dialog.component.html'
})
export class ZoneDescriptionDialogComponent implements OnInit {

    zoneDescription: ZoneDescription;
    authorities: any[];
    isSaving: boolean;

    zones: Zone[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private zoneDescriptionService: ZoneDescriptionService,
        private zoneService: ZoneService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['zoneDescription']);
        this.zoneDescription = new ZoneDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.zoneService.query().subscribe(
            (res: Response) => { this.zones = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.zoneDescription.id !== undefined) {
            this.zoneDescriptionService.update(this.zoneDescription)
                .subscribe((res: ZoneDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.zoneDescriptionService.create(this.zoneDescription)
                .subscribe((res: ZoneDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ZoneDescription) {
        this.eventManager.broadcast({ name: 'zoneDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.zoneDescription = result;
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

    trackZoneById(index: number, item: Zone) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-zone-description-popup',
    template: ''
})
export class ZoneDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private zoneDescriptionPopupService: ZoneDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.zoneDescriptionPopupService
                    .open(ZoneDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.zoneDescriptionPopupService
                    .open(ZoneDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
