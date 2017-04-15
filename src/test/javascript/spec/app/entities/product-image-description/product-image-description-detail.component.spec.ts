import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductImageDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/product-image-description/product-image-description-detail.component';
import { ProductImageDescriptionService } from '../../../../../../main/webapp/app/entities/product-image-description/product-image-description.service';
import { ProductImageDescription } from '../../../../../../main/webapp/app/entities/product-image-description/product-image-description.model';

describe('Component Tests', () => {

    describe('ProductImageDescription Management Detail Component', () => {
        let comp: ProductImageDescriptionDetailComponent;
        let fixture: ComponentFixture<ProductImageDescriptionDetailComponent>;
        let service: ProductImageDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ProductImageDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductImageDescriptionService,
                    EventManager
                ]
            }).overrideComponent(ProductImageDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductImageDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductImageDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductImageDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productImageDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
