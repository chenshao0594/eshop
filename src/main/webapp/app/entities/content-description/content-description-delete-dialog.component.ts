import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ContentDescription } from './content-description.model';
import { ContentDescriptionPopupService } from './content-description-popup.service';
import { ContentDescriptionService } from './content-description.service';

@Component({
    selector: 'jhi-content-description-delete-dialog',
    templateUrl: './content-description-delete-dialog.component.html'
})
export class ContentDescriptionDeleteDialogComponent {

    contentDescription: ContentDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private contentDescriptionService: ContentDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['contentDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contentDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contentDescriptionListModification',
                content: 'Deleted an contentDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-content-description-delete-popup',
    template: ''
})
export class ContentDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contentDescriptionPopupService: ContentDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.contentDescriptionPopupService
                .open(ContentDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
