import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ManufacturerDescription } from './manufacturer-description.model';
import { ManufacturerDescriptionPopupService } from './manufacturer-description-popup.service';
import { ManufacturerDescriptionService } from './manufacturer-description.service';
import { Manufacturer, ManufacturerService } from '../manufacturer';

@Component({
    selector: 'jhi-manufacturer-description-dialog',
    templateUrl: './manufacturer-description-dialog.component.html'
})
export class ManufacturerDescriptionDialogComponent implements OnInit {

    manufacturerDescription: ManufacturerDescription;
    authorities: any[];
    isSaving: boolean;

    manufacturers: Manufacturer[];
        constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private manufacturerDescriptionService: ManufacturerDescriptionService,
        private manufacturerService: ManufacturerService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['manufacturerDescription']);
        this.manufacturerDescription = new ManufacturerDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.manufacturerService.query().subscribe(
            (res: Response) => { this.manufacturers = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.manufacturerDescription.id !== undefined) {
            this.manufacturerDescriptionService.update(this.manufacturerDescription)
                .subscribe((res: ManufacturerDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.manufacturerDescriptionService.create(this.manufacturerDescription)
                .subscribe((res: ManufacturerDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ManufacturerDescription) {
        this.eventManager.broadcast({ name: 'manufacturerDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.manufacturerDescription = result;
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

    trackManufacturerById(index: number, item: Manufacturer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-manufacturer-description-popup',
    template: ''
})
export class ManufacturerDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private manufacturerDescriptionPopupService: ManufacturerDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.manufacturerDescriptionPopupService
                    .open(ManufacturerDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.manufacturerDescriptionPopupService
                    .open(ManufacturerDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
