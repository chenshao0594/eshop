import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { TaxRateDescription } from './tax-rate-description.model';
import { TaxRateDescriptionPopupService } from './tax-rate-description-popup.service';
import { TaxRateDescriptionService } from './tax-rate-description.service';

@Component({
    selector: 'jhi-tax-rate-description-delete-dialog',
    templateUrl: './tax-rate-description-delete-dialog.component.html'
})
export class TaxRateDescriptionDeleteDialogComponent {

    taxRateDescription: TaxRateDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private taxRateDescriptionService: TaxRateDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['taxRateDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.taxRateDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'taxRateDescriptionListModification',
                content: 'Deleted an taxRateDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tax-rate-description-delete-popup',
    template: ''
})
export class TaxRateDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taxRateDescriptionPopupService: TaxRateDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.taxRateDescriptionPopupService
                .open(TaxRateDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
