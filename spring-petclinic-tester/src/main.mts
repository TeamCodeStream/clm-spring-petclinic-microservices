import got from "got";

// const HOST = "http://localhost:8080";
const HOST = "http://api-gateway:9999";

async function testApp() {
  while (true) {
    try {
      const ownerResponse = await got(`${HOST}/api/customer/owners`);
      const ownerBody = JSON.parse(ownerResponse.body);
      console.info(`Found ${ownerBody.length} owners`);

      const ownerByIdResponse = await got(`${HOST}/api/gateway/owners/4`);
      const ownerById = JSON.parse(ownerByIdResponse.body);
      console.info(`Owner by id returned ${ownerById["firstName"]}`);

      const editOwnerRequest = {
        firstName: "Harold",
        lastName: "Davies",
        address: "564 Friendly St",
        city: "Windsor",
        id: 5,
        telephone: "111222333444",
      };

      const editOwnerResponse = await got.put(`${HOST}/api/customer/owners/4`, {
        json: editOwnerRequest,
      });

      if (editOwnerResponse.ok) {
        console.info("Edit owner success");
      } else {
        const error = editOwnerResponse.body;
        console.error(`Edit owner error ${error}`);
      }

      const vetsResponse = await got(`${HOST}/api/vet/vets`);
      const vetsBody = JSON.parse(vetsResponse.body);
      console.info(`Found ${vetsBody.length} vets`);
    } catch (e) {
      if (e instanceof Error) {
        console.warn(`Warning: ${e.message}`);
      } else {
        console.warn(`Warning`, e);
      }
    }
    await delay(5000);
  }
}

function delay(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

await testApp();

export {};
