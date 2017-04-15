import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ManufacturerDescription } from './manufacturer-description.model';
import { ManufacturerDescriptionPopupService } from './manufacturer-description-popup.service';
import { ManufacturerDescriptionService } from './manufacturer-description.service';

@Component({
    selector: 'jhi-manufacturer-description-delete-dialog',
    templateUrl: './manufacturer-description-delete-dialog.component.html'
})
export class ManufacturerDescriptionDeleteDialogComponent {

    manufacturerDescription: ManufacturerDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private manufacturerDescriptionService: ManufacturerDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['manufacturerDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.manufacturerDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'manufacturerDescriptionListModification',
                content: 'Deleted an manufacturerDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-manufacturer-description-delete-popup',
    template: ''
})
export class ManufacturerDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private manufacturerDescriptionPopupService: ManufacturerDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.manufacturerDescriptionPopupService
                .open(ManufacturerDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
